package com.perennial.exam.service;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.beans.CostOfDeliveryResponse;

/**
 * CostOfDeliveryService Interface
 *
 * @author Shailesh Totale
 */
public interface CostOfDeliveryService {
        CostOfDeliveryResponse getCostOfDelivery(CostOfDeliveryRequest pCostOfDeliveryRequest);
}
