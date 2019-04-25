package com.xmw.qiyun.data.model.net.publish;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class PublishBodyListBean extends CommonResponse {

    private PublishBodyList Data;

    public PublishBodyList getData() {
        return Data;
    }

    public void setData(PublishBodyList data) {
        Data = data;
    }
}
