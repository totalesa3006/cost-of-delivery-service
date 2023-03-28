package com.perennial.exam.exception.code;

public enum VoucherErrorCode implements ErrorCode {

    SERVICE_EXCEPTION("Voucher service failed ...  Please retry", "EXAM-2001");

    private String errorCode;
    private String errorMessage;

    private VoucherErrorCode(String errorMessage,String errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
