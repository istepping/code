package com.xmw.qiyun.data.model.event;

/**
 * Created by dell on 2018/2/6.
 */

public class ResultEvent {

    private boolean isSuccess;

    public ResultEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
