package com.tech24.kolorio.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by oscarmakala on 14/07/2017.
 */
public class Response {
    private final InputStream stream;
    private final String content;
    private final int statusCode;

    /**
     * Create a Response from content string and status code.
     *
     * @param content    content string
     * @param statusCode status code
     */
    public Response(final String content, final int statusCode) {
        this.stream = null;
        this.content = content;
        this.statusCode = statusCode;
    }

    /**
     * Create a Response from input stream and status code.
     *
     * @param stream     input stream
     * @param statusCode status code
     */
    public Response(final InputStream stream, final int statusCode) {
        this.stream = stream;
        this.content = null;
        this.statusCode = statusCode;
    }


    public int getStatusCode() {
        return this.statusCode;
    }

    public InputStream getStream() {
        if (stream != null) {
            return stream;
        }
        try {
            return new ByteArrayInputStream(content.getBytes("utf-8"));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }
}
