package com.perennial.exam.exception;

import com.perennial.exam.exception.code.ErrorCode;
import com.perennial.exam.exception.code.CostOfDeliveryErrorCode;

public class InvalidDataException extends BaseException {

    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidDataException(Throwable throwable, String errorMessage) {
        super(throwable, errorMessage);
    }

    public InvalidDataException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CostOfDeliveryErrorCode getErrorCode() {
        return (CostOfDeliveryErrorCode)(this.errorCode);
    }
}
