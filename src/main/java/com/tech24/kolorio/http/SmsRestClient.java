package com.tech24.kolorio.http;


import com.google.common.base.Predicate;

/**
 * Created by oscarmakala on 14/07/2017.
 */
public class SmsRestClient {
    public static final Predicate<Integer> SUCCESS = new Predicate<Integer>() {
        @Override
        public boolean apply(Integer httpCode) {
            return httpCode != null && httpCode >= 200 && httpCode < 300;
        }
    };
    private final String password;
    private final String username;
    private final HttpClient httpClient;
    private final String url;
    private final int type;
    private final int deliveryReport;


    public SmsRestClient(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.type = builder.type;

        this.httpClient = builder.httpClient;
        this.deliveryReport = builder.deliveryReport;
        this.url = builder.url;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Make a request to Twilio.
     *
     * @param request request to make
     * @return Response object
     */
    public Response request(final Request request) {
        request.setAuth(username, password);
        request.setAdditionalParams(type, deliveryReport);
        return httpClient.reliableRequest(request);
    }

    public static class Builder {
        private String username;
        private String password;
        private String url;
        private HttpClient httpClient;
        private int type;
        private int deliveryReport;

        /**
         * Create a new sms rest client
         *
         * @param username username to use
         * @param password password for the username
         */
        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }


        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        /**
         * Build new {@link SmsRestClient}
         *
         * @return SmsRestClient instance
         */
        public SmsRestClient build() {
            if (this.httpClient == null) {
                this.httpClient = new NetworkHttpClient();
            }
            return new SmsRestClient(this);
        }


        public Builder setDeliveryReport(int deliveryReport) {
            this.deliveryReport = deliveryReport;
            return this;
        }
    }
}
