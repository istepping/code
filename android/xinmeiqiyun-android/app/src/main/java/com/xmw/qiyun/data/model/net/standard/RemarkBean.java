package com.xmw.qiyun.data.model.net.standard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/1.
 */

public class RemarkBean {

    private List<Standard> Data = new ArrayList<>();

    public List<Standard> getData() {
        return Data;
    }

    public void setData(List<Standard> data) {
        Data.addAll(data);
    }
}
