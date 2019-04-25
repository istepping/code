package com.xmw.qiyun.data.model.net.cargo;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoOwnerBean extends CommonResponse {

    private CargoOwner Data;

    public CargoOwner getData() {
        return Data;
    }

    public void setData(CargoOwner data) {
        Data = data;
    }
}
