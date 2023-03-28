package com.perennial.exam.ruleengine.rule;

import static com.perennial.exam.constant.CostOfDeliveryConstants.DECIOSN_ACCPET;
import static com.perennial.exam.constant.CostOfDeliveryConstants.DECIOSN_REJECT;
import static com.perennial.exam.constant.CostOfDeliveryConstants.REJECT_ERROR_MSG;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.ruleengine.result.CostOfDeliveryRuleResult;
import lombok.Builder;

@Builder
public class WeightBasedCostOfDeliveryRule implements CostOfDeliveryRule {

  private int priority;
  private String ruleName;
  private float minWeight;
  private float maxWeight;
  private float costFactor;

  public CostOfDeliveryRuleResult applyRule(CostOfDeliveryRequest pCostOfDeliveryRequest) {
    boolean isApplicable = false;
    float lDeliveryCost = 1.0f;
    String lDecision = DECIOSN_ACCPET;
    String lMessage = "";
    if (pCostOfDeliveryRequest.getWeight() > minWeight
        && pCostOfDeliveryRequest.getWeight() <= maxWeight) {
      isApplicable = true;
      lDeliveryCost = lDeliveryCost * pCostOfDeliveryRequest.getWeight() * costFactor;
    }
    if (lDeliveryCost < 0.0) {
      lDecision = DECIOSN_REJECT;
      lDeliveryCost =0.0f;
      lMessage =REJECT_ERROR_MSG;
    }
    CostOfDeliveryRuleResult lCostOfDeliveryRuleResult = CostOfDeliveryRuleResult.builder()
        .isApplicable(isApplicable).costOfDelivery(lDeliveryCost).ruleName(ruleName)
        .decision(lDecision).message(lMessage).build();
    ;

    return lCostOfDeliveryRuleResult;
  }

}
