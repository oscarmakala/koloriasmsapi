package com.tech24.kolorio.v1;

import com.tech24.kolorio.base.Creator;
import com.tech24.kolorio.exception.RestException;
import com.tech24.kolorio.http.HttpMethod;
import com.tech24.kolorio.http.Request;
import com.tech24.kolorio.http.Response;
import com.tech24.kolorio.http.SmsRestClient;

/**
 * Created by oscarmakala on 14/07/2017.
 */
public class MessageCreator extends Creator<Message> {

    private final String to;
    private final String from;
    private final String body;

    public MessageCreator(final String to, final String from, final String body) {
        this.to = to;
        this.from = from;
        this.body = body;
    }

    public Message create(final SmsRestClient client) {
        Request request = new Request(HttpMethod.POST, client.getUrl());
        addPostParams(request);
        Response response = client.request(request);
        if (response == null) {
            throw new RuntimeException("Message creation failed: Unable to connect to server");
        } else if (!SmsRestClient.SUCCESS.apply(response.getStatusCode())) {
            RestException restException = RestException.fromString(response.getStream());
            if (restException == null) {
                throw new RuntimeException("Server Error, no content");
            }
        }
        return Message.from(response.getStream());
    }

    /**
     * Add the requested post parameters to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (to != null) {
            request.addPostParam("destination", to);
        }
        if (from != null) {
            request.addPostParam("source", to);
        }
        if (body != null) {
            request.addPostParam("message", body);
        }

    }
}
