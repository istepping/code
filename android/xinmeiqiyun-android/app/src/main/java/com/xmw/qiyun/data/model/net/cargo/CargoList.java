package com.xmw.qiyun.data.model.net.cargo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoList {

    private int Total;
    private List<Cargo> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<Cargo> getResultData() {
        return ResultData;
    }

    public void setResultData(List<Cargo> resultData) {
        ResultData.addAll(resultData);
    }
}
