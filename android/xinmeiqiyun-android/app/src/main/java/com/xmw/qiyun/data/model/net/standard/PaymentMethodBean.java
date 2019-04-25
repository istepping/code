package com.xmw.qiyun.data.model.net.standard;

import com.xmw.qiyun.data.model.net.CommonResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/1.
 */

public class PaymentMethodBean extends CommonResponse {

    private List<Standard> Data = new ArrayList<>();

    public List<Standard> getData() {
        return Data;
    }

    public void setData(List<Standard> data) {
        Data.addAll(data);
    }
}
