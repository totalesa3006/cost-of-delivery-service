package com.perennial.exam.exception;

import com.perennial.exam.exception.code.ErrorCode;
import com.perennial.exam.exception.code.VoucherErrorCode;

public class ServiceException extends BaseException {
    public ServiceException(String errorMessage) {
        super(errorMessage);
    }

    public ServiceException(Throwable throwable, String errorMessage) {
        super(throwable, errorMessage);
    }

   
    public ServiceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public VoucherErrorCode getErrorCode() {
        return (VoucherErrorCode) this.errorCode;
    }
}
