package com.xmw.qiyun.data.model.net.publish;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/31.
 */

public class PublishDetailBean extends CommonResponse {

    public PublishDetail Data;

    public PublishDetail getData() {
        return Data;
    }

    public void setData(PublishDetail data) {
        Data = data;
    }
}
