package com.xmw.qiyun.data.model.net;

import java.io.Serializable;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class CommonResponse implements Serializable {

    private int StatusCode;
    private boolean Result;
    private String Message;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
