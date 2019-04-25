package com.xmw.qiyun.data.model.event;

/**
 * Created by hongyuan on 2017/9/7.
 */

public class PublishStatusEvent {

    private boolean isShow;

    public PublishStatusEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
