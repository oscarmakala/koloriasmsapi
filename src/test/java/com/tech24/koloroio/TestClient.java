package com.tech24.koloroio;

import com.tech24.kolorio.http.SmsRestClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public class TestClient {

    private SmsRestClient client;

    @Test
    public void testSendSms() {
//        Message message = new MessageCreator(
//                "",
//                "INFO",
//                "test"
//        ).create(client);
//        if (message != null) {
//
//        }
    }

    @Before
    public void init() {
        if (client == null) {
            client = new SmsRestClient.Builder("", "")
                    .setUrl("")
                    .setType(0)
                    .setDeliveryReport(0)
                    .build();
        }
    }
}
