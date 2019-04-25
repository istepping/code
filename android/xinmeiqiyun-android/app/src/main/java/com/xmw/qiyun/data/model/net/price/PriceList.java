package com.xmw.qiyun.data.model.net.price;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/18.
 */

public class PriceList {

    private int Total;
    private List<Price> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<Price> getResultData() {
        return ResultData;
    }

    public void setResultData(List<Price> resultData) {
        ResultData.addAll(resultData);
    }
}
