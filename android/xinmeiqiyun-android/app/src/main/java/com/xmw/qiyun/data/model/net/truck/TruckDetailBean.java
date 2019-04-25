package com.xmw.qiyun.data.model.net.truck;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckDetailBean extends CommonResponse {

    private TruckDetail Data;

    public TruckDetail getData() {
        return Data;
    }

    public void setData(TruckDetail data) {
        Data = data;
    }
}
