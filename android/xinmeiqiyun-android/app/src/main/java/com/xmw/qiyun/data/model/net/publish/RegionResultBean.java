package com.xmw.qiyun.data.model.net.publish;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class RegionResultBean extends CommonResponse {

    private RegionResult Data;

    public RegionResult getData() {
        return Data;
    }

    public void setData(RegionResult data) {
        Data = data;
    }
}
