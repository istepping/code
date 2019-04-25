package com.xmw.qiyun.data.model.net.store;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by dell on 2018/1/31.
 */

public class WxResultBean extends CommonResponse {

    private WxResult Data;

    public WxResult getData() {
        return Data;
    }

    public void setData(WxResult data) {
        Data = data;
    }
}
