package com.perennial.exam.ruleengine.result;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CostOfDeliveryRuleResult {

  private boolean isApplicable;
  private String ruleName;
  private float costOfDelivery;
  private String decision;
  private String message;

}
