package com.xmw.qiyun.data.model.net.other;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/9/7.
 */

public class StatusBean extends CommonResponse {

    private Status Data;

    public Status getData() {
        return Data;
    }

    public void setData(Status data) {
        Data = data;
    }
}
