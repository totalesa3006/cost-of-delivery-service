package com.perennial.exam.exception;

import com.perennial.exam.exception.code.ErrorCode;

public class BaseException extends RuntimeException {
  protected Throwable throwable;
  protected String errorMessage;
  protected ErrorCode errorCode;

  public BaseException(String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
  }

  public BaseException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public BaseException(Throwable throwable, String errorMessage) {
    super(errorMessage, throwable);
    this.throwable = throwable;
    this.errorMessage = errorMessage;
  }
}
