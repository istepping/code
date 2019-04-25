package com.xmw.qiyun.data.model.net.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/13.
 */

public class TruckMarkerList {

    private int Total;
    private List<TruckMarker> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<TruckMarker> getResultData() {
        return ResultData;
    }

    public void setResultData(List<TruckMarker> resultData) {
        ResultData.addAll(resultData);
    }
}
