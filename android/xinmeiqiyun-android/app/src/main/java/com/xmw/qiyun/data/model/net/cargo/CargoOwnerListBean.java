package com.xmw.qiyun.data.model.net.cargo;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by dell on 2017/11/3.
 */

public class CargoOwnerListBean extends CommonResponse {

    private CargoOwnerList Data;

    public CargoOwnerList getData() {
        return Data;
    }

    public void setData(CargoOwnerList data) {
        Data = data;
    }
}
