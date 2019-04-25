package com.xmw.qiyun.data.model.net.price;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/9/18.
 */

public class PriceListBean extends CommonResponse {

    private PriceList Data;

    public PriceList getData() {
        return Data;
    }

    public void setData(PriceList data) {
        Data = data;
    }
}
