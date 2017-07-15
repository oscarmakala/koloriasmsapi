package com.tech24.kolorio.http;

import com.google.common.collect.Lists;
import com.tech24.kolorio.exception.InvalidRequestException;
import org.apache.commons.codec.Charsets;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by oscarmakala on 14/07/2017.
 */

public class NetworkHttpClient extends HttpClient {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 30500;
    private final org.apache.http.client.HttpClient client;

    /**
     * Create a new HTTP Client.
     */
    public NetworkHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        Collection<Header> headers = Lists.<Header>newArrayList(
                new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8")
        );
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.useSystemProperties();

        clientBuilder
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .setDefaultRequestConfig(config)
                .setDefaultHeaders(headers)
                .setMaxConnPerRoute(10);

        client = clientBuilder.build();
    }


    @Override
    public Response makeRequest(Request request) {
        HttpMethod method = request.getMethod();
        RequestBuilder builder = RequestBuilder.create(method.toString())
                .setUri(request.constructURL().toString())
                .setVersion(HttpVersion.HTTP_1_1)
                .setCharset(Charsets.UTF_8);
        if (method == HttpMethod.POST) {
            builder.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            for (Map.Entry<String, List<String>> entry : request.getPostParams().entrySet()) {
                for (String value : entry.getValue()) {
                    builder.addParameter(entry.getKey(), value);
                }
            }
        }

        HttpResponse response = null;

        try {
            response = client.execute(builder.build());
            HttpEntity entity = response.getEntity();
            return new Response(
                    // Consume the entire HTTP response before returning the stream
                    entity == null ? null : new BufferedHttpEntity(entity).getContent(),
                    response.getStatusLine().getStatusCode()
            );
        } catch (IOException e) {
            throw new InvalidRequestException(e.getMessage());
        } finally {
            // Ensure this response is properly closed
            HttpClientUtils.closeQuietly(response);
        }
    }
}
