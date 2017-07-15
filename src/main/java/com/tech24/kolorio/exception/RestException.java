package com.tech24.kolorio.exception;

import com.tech24.kolorio.util.ApiUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public class RestException {
    private String code;
    private String cellNo;
    private String messageId;

    public static RestException fromString(InputStream stream) {
        try {
            String result = ApiUtil.readAsString(stream);
            return createRestException(result);
        } catch (IOException e) {
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

    private static RestException createRestException(String result) {
        try {
            String[] paramArray = result.split("\\|");
            RestException restException = new RestException();
            restException.code = paramArray[0];
            if (paramArray.length > 1) {
                restException.cellNo = paramArray[1];
            }
            if (paramArray.length > 2) {
                restException.messageId = paramArray[2];
            }
            return restException;
        } catch (NullPointerException e) {
            throw new ApiException(e.getMessage());
        }
    }


}
