package com.xmw.qiyun.data.model.net.truck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckList {

    private int Total;
    private List<Truck> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<Truck> getResultData() {
        return ResultData;
    }

    public void setResultData(List<Truck> resultData) {
        ResultData = resultData;
    }
}
