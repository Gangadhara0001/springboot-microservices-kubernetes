package com.example.exception;

public enum ErrorConfig {
    RESOURCE_NOT_FOUND("EMPLOYEE_404", "Resource not found"),
    VALIDATION_FAILED("EMPLOYEE_400", "Validation failed"),
    INTERNAL_SERVER_ERROR("EMPLOYEE_500", "Internal server error");


    private String errorCode;
    private String errorDescription;

    ErrorConfig(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
