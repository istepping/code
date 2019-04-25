package com.xmw.qiyun.data.model.net.publish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class PublishStreamList {

    private int Total;
    private List<PublishStream> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<PublishStream> getResultData() {
        return ResultData;
    }

    public void setResultData(List<PublishStream> resultData) {
        ResultData.addAll(resultData);
    }
}
