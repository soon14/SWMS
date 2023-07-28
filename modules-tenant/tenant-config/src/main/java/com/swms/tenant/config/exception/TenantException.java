package com.swms.tenant.config.exception;

public class TenantException extends RuntimeException {

    public TenantException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantException(String message) {
        super(message);
    }
}
