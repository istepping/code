package com.xmw.qiyun.data.model.net.other;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VersionBean extends CommonResponse {

    private Version Data;

    public Version getData() {
        return Data;
    }

    public void setData(Version data) {
        Data = data;
    }
}
