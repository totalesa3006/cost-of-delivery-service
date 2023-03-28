package com.perennial.exam.ruleengine.rule;

import static com.perennial.exam.constant.CostOfDeliveryConstants.DECIOSN_ACCPET;
import static com.perennial.exam.constant.CostOfDeliveryConstants.DECIOSN_REJECT;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.ruleengine.result.CostOfDeliveryRuleResult;
import lombok.Builder;

@Builder
public class GenericCostOfDeliveryRule implements CostOfDeliveryRule {


  private String ruleName;
  private float minWeight;
  private float maxWeight;
  private float minVolume;
  private float maxVolume;
  private float costFactor;

  public CostOfDeliveryRuleResult applyRule(CostOfDeliveryRequest pCostOfDeliveryRequest) {
    boolean isApplicable = false;
    float lDeliveryCost = 1.0f;
    String lDecision = DECIOSN_ACCPET;
    if (pCostOfDeliveryRequest.getWeight() > minWeight
        && pCostOfDeliveryRequest.getWeight() <= maxWeight
        && pCostOfDeliveryRequest.getVolume() > minVolume
        && pCostOfDeliveryRequest.getVolume() <= maxVolume) {
      isApplicable = true;
      lDeliveryCost =
          lDeliveryCost * pCostOfDeliveryRequest.getWeight() * pCostOfDeliveryRequest.getVolume()
              * costFactor;
    }
    if (lDeliveryCost < 0.0) {
      lDecision = DECIOSN_REJECT;
    }
    CostOfDeliveryRuleResult lCostOfDeliveryRuleResult = CostOfDeliveryRuleResult.builder()
        .isApplicable(isApplicable).costOfDelivery(lDeliveryCost).ruleName(ruleName)
        .decision(lDecision).build();

    return lCostOfDeliveryRuleResult;
  }

}
