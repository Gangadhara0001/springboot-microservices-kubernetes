package com.example.exception;


public class EmployeeServiceBaseException extends RuntimeException {
    private String errorCode;
    private String errorDescription;


    public EmployeeServiceBaseException(String message){
        super(message);
    }

    public EmployeeServiceBaseException(String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public EmployeeServiceBaseException(String errorCode, String errorDescription, Throwable cause) {
        super(errorDescription,cause);
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
