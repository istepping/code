package com.xmw.qiyun.data.model.net.publish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class PublishBodyList {

    private int Total;
    private List<PublishBody> ResultData = new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<PublishBody> getResultData() {
        return ResultData;
    }

    public void setResultData(List<PublishBody> resultData) {
        ResultData.addAll(resultData);
    }
}
