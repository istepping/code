package com.xmw.qiyun.data.model.net.cargo;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoDetailBean extends CommonResponse {

    private CargoDetail Data;

    public CargoDetail getData() {
        return Data;
    }

    public void setData(CargoDetail data) {
        Data = data;
    }
}
