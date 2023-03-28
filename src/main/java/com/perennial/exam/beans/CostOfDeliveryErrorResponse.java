package com.perennial.exam.beans;

import lombok.Data;

@Data
public class CostOfDeliveryErrorResponse implements  ErrorResponse{


  private String message;
  private String errorCode;

  public CostOfDeliveryErrorResponse(String errorCode,String errorMessage) {
    this.message = errorMessage;
    this.errorCode = errorCode;
  }
}
