package com.perennial.exam.ruleengine.rule;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.ruleengine.result.CostOfDeliveryRuleResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface CostOfDeliveryRule {



  public CostOfDeliveryRuleResult applyRule(CostOfDeliveryRequest pCostOfDeliveryRequest) ;

}
