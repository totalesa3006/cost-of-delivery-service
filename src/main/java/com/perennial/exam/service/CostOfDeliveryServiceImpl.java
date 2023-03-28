package com.perennial.exam.service;

import static com.perennial.exam.constant.CostOfDeliveryConstants.COST_OF_DELIVERY_RULES_MAP;
import static com.perennial.exam.constant.CostOfDeliveryConstants.COST_OF_DELIVERY_RULES_PRIORITY_LIST;
import static com.perennial.exam.constant.CostOfDeliveryConstants.COST_OF_DELIVERY_RULES_PRIORITY_MAP;
import static com.perennial.exam.constant.ExceptionConstants.INVLAID_CODE;
import static com.perennial.exam.constant.ExceptionConstants.INVLAID_VOUCHER_CODE;
import static com.perennial.exam.constant.ExceptionConstants.VOUCHER_CODE_EXPIRED;
import static com.perennial.exam.constant.ExceptionConstants.VOUCHER_SERVICE_MESSAGE;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.beans.CostOfDeliveryResponse;
import com.perennial.exam.beans.VoucherCodeResponse;
import com.perennial.exam.client.VoucherServiceFiegnClient;
import com.perennial.exam.entity.CostOfDelivery;
import com.perennial.exam.exception.ServiceException;
import com.perennial.exam.repository.CostOfDeliveryRepository;
import com.perennial.exam.ruleengine.factory.CostOfDeliveryRuleFactory;
import com.perennial.exam.ruleengine.result.CostOfDeliveryRuleResult;
import com.perennial.exam.ruleengine.rule.CostOfDeliveryRule;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This class have methods for business logic.
 */
@Service
public class CostOfDeliveryServiceImpl implements CostOfDeliveryService {

  private static final Logger logger = LoggerFactory.getLogger(CostOfDeliveryServiceImpl.class);

  @Value("${voucher.service.apiKey}")
  private String voucherServiceAPIKey;

  private CostOfDeliveryRepository costOfDeliveryRepository;

  @Autowired
  @Qualifier(COST_OF_DELIVERY_RULES_MAP)
  private ConcurrentHashMap<String, CostOfDeliveryRule> costOfDeliveryRulesMap;

  @Autowired
  @Qualifier(COST_OF_DELIVERY_RULES_PRIORITY_MAP)
  private ConcurrentHashMap<String, List<String>> costOfDeliveryRulesPriorityMap;
  private VoucherServiceFiegnClient voucherServiceFiegnClient;

  public CostOfDeliveryServiceImpl(VoucherServiceFiegnClient voucherServiceFiegnClient,
      CostOfDeliveryRepository costOfDeliveryRepository) {
    this.voucherServiceFiegnClient = voucherServiceFiegnClient;
    this.costOfDeliveryRepository = costOfDeliveryRepository;
  }


  private void populdateCostOfDeliveryRulesMap() {

    if (costOfDeliveryRulesMap.isEmpty() || costOfDeliveryRulesMap.size() ==0) {
      List<String> rulesPriorityList = new ArrayList<>();
      List<CostOfDelivery> costOfDeliveryList = costOfDeliveryRepository.findAllByOrderByPriorityAsc();
      for (CostOfDelivery costOfDelivery : costOfDeliveryList) {
        CostOfDeliveryRule lCostOfDeliveryRule = CostOfDeliveryRuleFactory.getCostOfDeliveryRule(
            costOfDelivery);
        costOfDeliveryRulesMap.put(costOfDelivery.getRuleName(), lCostOfDeliveryRule);
        rulesPriorityList.add(costOfDelivery.getRuleName());
      }
      costOfDeliveryRulesPriorityMap.put(COST_OF_DELIVERY_RULES_PRIORITY_LIST, rulesPriorityList);
    }

  }

  public CostOfDeliveryResponse getCostOfDelivery(CostOfDeliveryRequest pCostOfDeliveryRequest) {
    CostOfDeliveryResponse lCostOfDeliveryResponse = CostOfDeliveryResponse.builder().build();
    try {

      populdateCostOfDeliveryRulesMap();
      if (StringUtils.isNotBlank(pCostOfDeliveryRequest.getVoucherCode())) {
        VoucherCodeResponse lVoucherCodeResponse = voucherServiceFiegnClient.getVoucherDetails(
            pCostOfDeliveryRequest.getVoucherCode(), voucherServiceAPIKey);
        boolean isVoucherExpired = isVoucherExpired(lVoucherCodeResponse.getExpiry());
        if (!isVoucherExpired
            || pCostOfDeliveryRequest.getIgnoreExpiredVoucherCode()) {

          List<String> rulesPriorityList = costOfDeliveryRulesPriorityMap.get(
              COST_OF_DELIVERY_RULES_PRIORITY_LIST);
          //rulesPriorityList.stream().takeWhile(p-> costOfDeliveryRulesMap.get(p).applyRule(pCostOfDeliveryRequest).isApplicable()).map()
          for (String ruleName : rulesPriorityList) {
            CostOfDeliveryRuleResult lCostOfDeliveryRuleResult = costOfDeliveryRulesMap.get(
                ruleName).applyRule(pCostOfDeliveryRequest);
            if (lCostOfDeliveryRuleResult.isApplicable()) {

              float costOfDelivery = lCostOfDeliveryRuleResult.getCostOfDelivery();
              if (!isVoucherExpired) {
                float discountPercent = lVoucherCodeResponse.getDiscount();

                costOfDelivery = costOfDelivery - (costOfDelivery * discountPercent) / 100;
              }
              lCostOfDeliveryResponse = lCostOfDeliveryResponse.toBuilder()
                  .typeOfParcel(lCostOfDeliveryRuleResult.getRuleName())
                  .costOfParcel(costOfDelivery).decision(lCostOfDeliveryRuleResult.getDecision())
                  .message(lCostOfDeliveryRuleResult.getMessage()).build();
              break;
            }
          }
        } else {
          throw new ServiceException(VOUCHER_CODE_EXPIRED);
        }
      }
    } catch (Exception ex) {
      String lErrorStack = ExceptionUtils.getStackTrace(ex);
      logger.error(
          "Exception:{} occurred while processing Mynt Voucher Code Service:{} ",
          lErrorStack,
          pCostOfDeliveryRequest.getVoucherCode());

      String lErrorMwessage = VOUCHER_SERVICE_MESSAGE;
      if (lErrorStack.contains(INVLAID_CODE)) {
        lErrorMwessage = INVLAID_VOUCHER_CODE;
      } else if (ex.getMessage().contains(VOUCHER_CODE_EXPIRED)) {
        lErrorMwessage = VOUCHER_CODE_EXPIRED;
      }

      throw new ServiceException(lErrorMwessage);
    }
    return lCostOfDeliveryResponse;
  }

  private boolean isVoucherExpired(Date pVoucherExpiryDate) {
    LocalDate lVoucherExpiryDate = pVoucherExpiryDate.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

    LocalDate lCurrentDate = LocalDate.now();
    return lVoucherExpiryDate.isBefore(lCurrentDate);
  }

  public String getVoucherServiceAPIKey() {
    return voucherServiceAPIKey;
  }
}
