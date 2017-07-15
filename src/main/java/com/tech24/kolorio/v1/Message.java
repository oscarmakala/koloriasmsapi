package com.tech24.kolorio.v1;

import com.tech24.kolorio.base.Resource;
import com.tech24.kolorio.exception.ApiException;
import com.tech24.kolorio.util.ApiUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public class Message extends Resource {
    private String code;
    private String cellNo;
    private String messageId;

    public static Message from(InputStream stream) {
        try {
            String result = ApiUtil.readAsString(stream);
            return createMessage(result);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

    private static Message createMessage(String result) {
        try {
            String[] paramArray = result.split("\\|");
            Message message = new Message();
            message.code = paramArray[0];
            if (paramArray.length > 1) {
                message.cellNo = paramArray[1];
            }
            if (paramArray.length > 2) {
                message.messageId = paramArray[2];
            }
            return message;
        } catch (NullPointerException e) {
            throw new ApiException(e.getMessage());
        }
    }

    public String getCode() {
        return code;
    }

    public String getCellNo() {
        return cellNo;
    }

    public String getMessageId() {
        return messageId;
    }
}
