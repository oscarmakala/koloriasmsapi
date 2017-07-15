package com.tech24.kolorio.base;

import com.google.common.util.concurrent.ListenableFuture;
import com.tech24.kolorio.SmsApiClient;
import com.tech24.kolorio.http.SmsRestClient;

import java.util.concurrent.Callable;

/**
 * Executor for creation of a resource.
 *
 * @param <T> type of the resource
 * @author oscarmakala
 */
public abstract class Creator<T extends Resource> {

    /**
     * Execute an async request using default client.
     *
     * @return future that resolves to requested object
     */
    public ListenableFuture<T> createAsync() {
        return createAsync(SmsApiClient.getRestClient());
    }

    /**
     * Execute an async request using specified client.
     *
     * @param client client used to make request
     * @return future that resolves to requested object
     */
    public ListenableFuture<T> createAsync(final SmsRestClient client) {
        return SmsApiClient.getExecutorService().submit(new Callable<T>() {
            public T call() {
                return create(client);
            }
        });
    }

    /**
     * Execute a request using default client.
     *
     * @return Requested object
     */
    public T create() {
        return create(SmsApiClient.getRestClient());
    }

    /**
     * Execute a request using specified client.
     *
     * @param client client used to make request
     * @return Requested object
     */
    public abstract T create(final SmsRestClient client);
}
