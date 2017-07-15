package com.tech24.kolorio.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tech24.kolorio.util.ApiUtil;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS");

    private final String method;

    HttpMethod(final String method) {
        this.method = method;
    }

    public String toString() {
        return method;
    }

    @JsonCreator
    public static HttpMethod forValue(final String value) {
        return ApiUtil.enumFromString(value, HttpMethod.values());
    }
}

