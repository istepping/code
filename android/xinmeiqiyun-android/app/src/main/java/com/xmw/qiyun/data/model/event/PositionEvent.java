package com.xmw.qiyun.data.model.event;

/**
 * Created by hongyuan on 2017/8/10.
 */

public class PositionEvent {

    private boolean isSuccess;

    public PositionEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
