package com.perennial.exam.beans;

import lombok.Builder;
import lombok.Data;

/**
 * @author Shailesh Totale
 */
@Data
@Builder(toBuilder = true)
public class CostOfDeliveryResponse implements SucessResponse {
      private String typeOfParcel;
      private float  costOfParcel;
      private String message;
      private String decision;
}
