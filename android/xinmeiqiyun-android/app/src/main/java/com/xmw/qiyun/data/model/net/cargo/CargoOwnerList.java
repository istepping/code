package com.xmw.qiyun.data.model.net.cargo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/3.
 */

public class CargoOwnerList {

    private int Total;
    private List<CargoOwner> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<CargoOwner> getResultData() {
        return ResultData;
    }

    public void setResultData(List<CargoOwner> resultData) {
        ResultData.addAll(resultData);
    }
}
