package com.tech24.kolorio.exception;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public class InvalidRequestException extends SmsApiException {
    private final String param;

    public InvalidRequestException(final String message) {
        super(message, null);
        this.param = null;
    }

    public InvalidRequestException(final String message, final String param) {
        super(message, null);
        this.param = param;
    }

    public InvalidRequestException(final String message, final String param, final Throwable cause) {
        super(message, cause);
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
