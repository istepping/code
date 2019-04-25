package com.xmw.qiyun.data.model.net.map;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by dell on 2017/10/13.
 */

public class TruckMarkerListBean extends CommonResponse {

    private TruckMarkerList Data;

    public TruckMarkerList getData() {
        return Data;
    }

    public void setData(TruckMarkerList data) {
        Data = data;
    }
}
