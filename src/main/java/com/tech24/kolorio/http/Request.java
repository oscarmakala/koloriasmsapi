package com.tech24.kolorio.http;

import com.google.common.base.Joiner;
import com.tech24.kolorio.exception.InvalidRequestException;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oscarmakala on 14/07/2017.
 */
public class Request {
    private final HttpMethod method;
    private final Map<String, List<String>> postParams;
    private final Map<String, List<String>> queryParams;
    private final String url;

    public Request(final HttpMethod method, final String url) {
        this.method = method;
        this.url = url;
        this.postParams = new HashMap<String, List<String>>();
        this.queryParams = new HashMap<String, List<String>>();
    }

    public void setAuth(final String username, final String password) {
        this.addPostParam("username", username);
        this.addPostParam("password", password);
    }

    /**
     * Add a form parameter.
     *
     * @param name  name of parameter
     * @param value value of parameter
     */
    public void addPostParam(final String name, final String value) {
        addParam(postParams, name, value);
    }

    private void addParam(final Map<String, List<String>> params, final String name, final String value) {
        if (!params.containsKey(name)) {
            params.put(name, new ArrayList<String>());
        }

        params.get(name).add(value);
    }

    public Map<String, List<String>> getPostParams() {
        return postParams;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public URL constructURL() {
        String params = encodeQueryParams();
        String stringUri = url;

        if (params.length() > 0) {
            stringUri += "?" + params;
        }

        try {
            URI uri = new URI(stringUri);
            return uri.toURL();
        } catch (final URISyntaxException e) {
            throw new RuntimeException("Bad URI: " + stringUri, e);
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Bad URL: " + stringUri, e);
        }
    }

    /**
     * Encode the query parameters.
     *
     * @return url encoded query parameters
     */
    public String encodeQueryParams() {
        return encodeParameters(queryParams);
    }

    private static String encodeParameters(final Map<String, List<String>> params) {
        List<String> parameters = new ArrayList<String>();

        for (final Map.Entry<String, List<String>> entry : params.entrySet()) {
            try {
                String encodedName = URLEncoder.encode(entry.getKey(), "UTF-8");
                for (final String value : entry.getValue()) {
                    if (value == null) {
                        continue;
                    }
                    String encodedValue = URLEncoder.encode(value, "UTF-8");
                    parameters.add(encodedName + "=" + encodedValue);
                }
            } catch (final UnsupportedEncodingException e) {
                throw new InvalidRequestException("Couldn't encode params", entry.getKey(), e);
            }
        }
        return Joiner.on("&").join(parameters);
    }


    public void setAdditionalParams(int type, int deliveryReport) {
        this.addPostParam("type", String.valueOf(type));
        this.addPostParam("dlr", String.valueOf(deliveryReport));
    }
}
