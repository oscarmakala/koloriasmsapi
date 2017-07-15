package com.tech24.kolorio;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.tech24.kolorio.http.SmsRestClient;

import java.util.concurrent.Executors;

/**
 * Created by oscarmakala on 14/07/2017.
 */
public class SmsApiClient {
    private static ListeningExecutorService executorService;
    private static SmsRestClient restClient;
    private static String username;
    private static String password;

    private SmsApiClient() {
    }


    /**
     * Initialize the SMSClient environment
     *
     * @param username account to use
     * @param password password for the account
     */
    public static void init(final String username, final String password) {
        SmsApiClient.setUsername(username);
        SmsApiClient.setPassword(password);
    }

    /**
     * Set the password
     *
     * @param password  Password of the SMPP Account
     * @throws RuntimeException when password is null
     */
    private static void setPassword(String password) {
        if (password == null) {
            throw new RuntimeException("Password can not be null");
        }
        if (!password.equals(SmsApiClient.password)) {
            SmsApiClient.invalidate();
        }
        SmsApiClient.password = password;
    }

    /**
     * Set the username
     *
     * @param username User name of the SMPP Account
     * @throws RuntimeException if account is null
     */
    private static void setUsername(String username) {
        if (username == null) {
            throw new RuntimeException("Username can not be null");
        }
        if (!username.equals(SmsApiClient.username)) {
            SmsApiClient.invalidate();
        }
        SmsApiClient.username = username;
    }

    /**
     * Invalidates the volatile state hel in SMSClient singleton
     */
    private static void invalidate() {
        SmsApiClient.restClient = null;
    }


    public static SmsRestClient getRestClient() {
        if (SmsApiClient.restClient == null) {
            if (SmsApiClient.username == null || SmsApiClient.password == null) {
                throw new RuntimeException("SmsRestClient was used before username and password were set, please call SMSClient.init()");
            }
            SmsRestClient.Builder builder = new SmsRestClient.Builder(SmsApiClient.username, SmsApiClient.password);
            SmsApiClient.restClient = builder.build();
        }
        return SmsApiClient.restClient;
    }

    public static ListeningExecutorService getExecutorService() {
        if (SmsApiClient.executorService == null) {
            SmsApiClient.executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        }
        return SmsApiClient.executorService;
    }
}
