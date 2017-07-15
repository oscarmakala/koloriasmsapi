package com.tech24.kolorio.exception;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public  abstract class SmsApiException  extends RuntimeException{
    public SmsApiException(final String message) {
        this(message, null);
    }

    public SmsApiException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
