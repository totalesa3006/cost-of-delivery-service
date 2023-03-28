package com.perennial.exam.exception.code;

import lombok.*;

/**
 *
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CostOfDeliveryErrorCode implements ErrorCode{

  private String exceptionName;
  private String errorMessage;
  private String errorCode;
}
