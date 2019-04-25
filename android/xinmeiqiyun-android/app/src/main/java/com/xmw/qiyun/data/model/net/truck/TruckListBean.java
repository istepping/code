package com.xmw.qiyun.data.model.net.truck;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckListBean extends CommonResponse {

    private TruckList Data;

    public TruckList getData() {
        return Data;
    }

    public void setData(TruckList data) {
        Data = data;
    }
}
