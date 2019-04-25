package com.xmw.qiyun.data.model.net.store;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by dell on 2018/2/5.
 */

public class AliResultBean extends CommonResponse {

    private AliResult Data;

    public AliResult getData() {
        return Data;
    }

    public void setData(AliResult data) {
        Data = data;
    }
}
