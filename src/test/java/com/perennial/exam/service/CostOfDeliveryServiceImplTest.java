package com.perennial.exam.service;

import static com.perennial.exam.constants.CostOfDeliveryTestConstants.COST_OF_DELIVERY_RULES_MAP;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.COST_OF_DELIVERY_RULES_PRIORITY_LIST;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.COST_OF_DELIVERY_RULES_PRIORITY_MAP;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.HEAVY_PARCEL;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.LARGE_PARCEL;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.MEDIUM_PARCEL;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.REJECT;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.SMALL_PARCEL;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.VOLUME;
import static com.perennial.exam.constants.CostOfDeliveryTestConstants.WEIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.beans.VoucherCodeResponse;
import com.perennial.exam.client.VoucherServiceFiegnClient;
import com.perennial.exam.config.ApplicationConfig;
import com.perennial.exam.config.ApplicationConfigTest;
import com.perennial.exam.entity.CostOfDelivery;
import com.perennial.exam.exception.ServiceException;
import com.perennial.exam.repository.CostOfDeliveryRepository;
import com.perennial.exam.ruleengine.rule.CostOfDeliveryRule;
import com.perennial.exam.ruleengine.rule.VolumeBasedCostOfDeliveryRule;
import com.perennial.exam.ruleengine.rule.WeightBasedCostOfDeliveryRule;
import feign.FeignException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

@Import(ApplicationConfigTest.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class CostOfDeliveryServiceImplTest {

  @Value("${voucher.service.apiKey}")
  private String voucherServiceAPIKey;

  @Mock(name=COST_OF_DELIVERY_RULES_MAP)
  private ConcurrentHashMap<String, CostOfDeliveryRule> costOfDeliveryRulesMap;

  @Mock(name=COST_OF_DELIVERY_RULES_PRIORITY_MAP)
  private ConcurrentHashMap<String, List<String>> costOfDeliveryRulesPriorityMap;

  @Mock
  private CostOfDeliveryRepository costOfDeliveryRepository;

  @Mock
  private VoucherServiceFiegnClient voucherServiceFiegnClient;

  @InjectMocks
  private CostOfDeliveryServiceImpl costOfDeliveryServiceImpl;

  @BeforeEach
  public void initialiseBeforeTest(){
    ReflectionTestUtils.setField(costOfDeliveryServiceImpl, "voucherServiceAPIKey", "apiKey");
    MockitoAnnotations.initMocks(this);

  }

  @Test
  void getCostOfDelivery() {
    CostOfDeliveryRequest pCostOfDeliveryRequest = CostOfDeliveryRequest.builder().height(20.0f)
        .weight(10.0f).length(10.0f).weight(49.0f).ignoreExpiredVoucherCode(true).voucherCode("MYNT")
        .build();
    String apiKey  =costOfDeliveryServiceImpl.getVoucherServiceAPIKey();
    testSetupFoAllTestCase();
    testSetupForValidVoucher();
    costOfDeliveryServiceImpl.getCostOfDelivery(pCostOfDeliveryRequest);


  }
  @Test
  void getCostOfDeliveryForExpiredVoucher() {
    CostOfDeliveryRequest pCostOfDeliveryRequest = CostOfDeliveryRequest.builder().height(20.0f)
        .weight(10.0f).length(10.0f).weight(49.0f).ignoreExpiredVoucherCode(false).voucherCode("MYNT")
        .build();
    String apiKey  =costOfDeliveryServiceImpl.getVoucherServiceAPIKey();
    testSetupFoAllTestCase();
    testSetupForVoucherExpired();
    Throwable exception = assertThrows(
        ServiceException.class, () -> {
          costOfDeliveryServiceImpl.getCostOfDelivery(pCostOfDeliveryRequest);
        }
    );

    assertEquals("Voucher code is expired.", exception.getMessage());


  }



  private void testSetupFoAllTestCase() {


    CostOfDelivery rejectDelivery =  new CostOfDelivery();
    rejectDelivery.setPriority(1);
    rejectDelivery.setCostFactor(-1.0f);
    rejectDelivery.setMinWeight(50.0f);
    rejectDelivery.setMaxWeight(9999999999.99f);
    rejectDelivery.setRuleName(REJECT);
    rejectDelivery.setSizeFactor(WEIGHT);


    CostOfDelivery heavyParcel =  new CostOfDelivery();
    heavyParcel.setPriority(2);
    heavyParcel.setCostFactor(20.0f);
    heavyParcel.setMinWeight(10.0f);
    heavyParcel.setMaxWeight(50.00f);
    heavyParcel.setRuleName(HEAVY_PARCEL);
    heavyParcel.setSizeFactor(WEIGHT);

    CostOfDelivery smallParcel =  new CostOfDelivery();
    smallParcel.setPriority(3);
    smallParcel.setCostFactor(0.03f);
    smallParcel.setMinVolume(0.0f);
    smallParcel.setMaxVolume(1500.00f);
    smallParcel.setRuleName(SMALL_PARCEL);
    smallParcel.setSizeFactor(VOLUME);

    CostOfDelivery mediumParcel =  new CostOfDelivery();
    mediumParcel.setPriority(4);
    mediumParcel.setCostFactor(0.04f);
    mediumParcel.setMinVolume(1500.0f);
    mediumParcel.setMaxVolume(2500.00f);
    mediumParcel.setRuleName(MEDIUM_PARCEL);
    mediumParcel.setSizeFactor(VOLUME);

    CostOfDelivery largeParcel =  new CostOfDelivery();
    largeParcel.setPriority(5);
    largeParcel.setCostFactor(0.05f);
    largeParcel.setMinVolume(2500.0f);
    largeParcel.setMaxVolume(9999999999.99f);
    largeParcel.setRuleName(LARGE_PARCEL);
    largeParcel.setSizeFactor(VOLUME);

    List<CostOfDelivery> costOfDeliveryList =  new ArrayList<>();
    costOfDeliveryList.add(rejectDelivery);
    costOfDeliveryList.add(heavyParcel);
    costOfDeliveryList.add(smallParcel);
    costOfDeliveryList.add(mediumParcel);
    costOfDeliveryList.add(largeParcel);

    when(costOfDeliveryRepository.findAllByOrderByPriorityAsc()).thenReturn(costOfDeliveryList);

    List<String> prioriytyList = new ArrayList<String>(
        Arrays.asList(REJECT,HEAVY_PARCEL,SMALL_PARCEL,MEDIUM_PARCEL,LARGE_PARCEL));


    when(costOfDeliveryRulesPriorityMap.get(COST_OF_DELIVERY_RULES_PRIORITY_LIST)).thenReturn(prioriytyList);


    CostOfDeliveryRule rejectDeliveryRule = WeightBasedCostOfDeliveryRule.builder()
        .ruleName(rejectDelivery.getRuleName()).costFactor(rejectDelivery.getCostFactor())
        .minWeight(rejectDelivery.getMinWeight()).maxWeight(rejectDelivery.getMaxWeight()).build();


    CostOfDeliveryRule heavyParcelRule = WeightBasedCostOfDeliveryRule.builder()
        .ruleName(heavyParcel.getRuleName()).costFactor(heavyParcel.getCostFactor())
        .minWeight(heavyParcel.getMinWeight()).maxWeight(heavyParcel.getMaxWeight()).build();


    CostOfDeliveryRule smallParcelRule = VolumeBasedCostOfDeliveryRule.builder()
        .ruleName(smallParcel.getRuleName()).costFactor(smallParcel.getCostFactor())
        .minVolume(smallParcel.getMinVolume()).maxVolume(smallParcel.getMaxVolume()).build();

    CostOfDeliveryRule mediumParcelRule = VolumeBasedCostOfDeliveryRule.builder()
        .ruleName(mediumParcel.getRuleName()).costFactor(mediumParcel.getCostFactor())
        .minVolume(mediumParcel.getMinVolume()).maxVolume(mediumParcel.getMaxVolume()).build();

    CostOfDeliveryRule largeParcelRule = VolumeBasedCostOfDeliveryRule.builder()
        .ruleName(largeParcel.getRuleName()).costFactor(largeParcel.getCostFactor())
        .minVolume(largeParcel.getMinVolume()).maxVolume(largeParcel.getMaxVolume()).build();

    when(costOfDeliveryRulesMap.get(REJECT)).thenReturn(rejectDeliveryRule);
    when(costOfDeliveryRulesMap.get(HEAVY_PARCEL)).thenReturn(heavyParcelRule);
    when(costOfDeliveryRulesMap.get(SMALL_PARCEL)).thenReturn(mediumParcelRule);
    when(costOfDeliveryRulesMap.get(MEDIUM_PARCEL)).thenReturn(smallParcelRule);
    when(costOfDeliveryRulesMap.get(LARGE_PARCEL)).thenReturn(largeParcelRule);
  }

  private void testSetupForValidVoucher() {
    VoucherCodeResponse lVoucherCodeResponse = VoucherCodeResponse.builder().code("MYNT").discount(12.25f).expiry(new Date()).build();
    when(voucherServiceFiegnClient.getVoucherDetails(any(),any())).thenReturn(lVoucherCodeResponse);
  }
  private void testSetupForVoucherExpired() {
    LocalDate lLocalDate = LocalDate.of(2015, 02, 20);

    VoucherCodeResponse lVoucherCodeResponse = VoucherCodeResponse.builder().code("MYNT").discount(12.25f).expiry(
        Date.from(lLocalDate.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant())).build();
    when(voucherServiceFiegnClient.getVoucherDetails(any(),any())).thenReturn(lVoucherCodeResponse);
  }

  private void testSetupForInvalidVoucher() {
    FeignException ex = Mockito.mock(FeignException.class);
    when(voucherServiceFiegnClient.getVoucherDetails(any(),any())).thenThrow(ex);
  }

}