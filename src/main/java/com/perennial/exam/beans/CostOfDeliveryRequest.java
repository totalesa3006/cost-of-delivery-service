package com.perennial.exam.beans;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CostOfDeliveryRequest {
  @NotNull(message = "Weight can not be null.")
  @DecimalMin(message = "Weight can not be less then 0.0001 KG" ,value="0.001")
  private Float weight;
  @NotNull(message = "Height can not be null.")
  @DecimalMin(message = "Height can not be less then 0.001 CM" ,value="0.001")
  private Float height;
  @NotNull(message = "Width can not be null.")
  @DecimalMin(message = "Width can not be less then 0.001 CM" ,value="0.001")
  private Float width;
  @DecimalMin(message = "Length can not be less then 0.001 CM" ,value="0.001")
  @NotNull(message = "Length can not be null.")
  private Float length;
  private String voucherCode;
  private Boolean ignoreExpiredVoucherCode;

  public float getVolume() {
    return height *width*length;
  }


}
