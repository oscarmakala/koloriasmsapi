package com.tech24.kolorio.exception;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public class ApiException extends SmsApiException{

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
