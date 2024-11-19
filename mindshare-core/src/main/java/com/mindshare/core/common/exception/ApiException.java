package com.mindshare.core.common.exception;

public class ApiException extends RuntimeException {

    private final Object[] errorMessageArgs;

    public ApiException(String message, Object[] errorMessageArgs) {
        super(message);
        this.errorMessageArgs = errorMessageArgs;
    }

    public ApiException(String message) {
        super(message);
        this.errorMessageArgs = null;
    }

    public Object[] getErrorMessageArgs() {
        return this.errorMessageArgs;
    }

}
