package com.xmw.qiyun.data.model.net.cargo;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoListBean extends CommonResponse {

    private CargoList Data;

    public CargoList getData() {
        return Data;
    }

    public void setData(CargoList data) {
        Data = data;
    }
}
