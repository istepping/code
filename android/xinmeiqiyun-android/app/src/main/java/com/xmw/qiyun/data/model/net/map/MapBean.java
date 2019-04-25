package com.xmw.qiyun.data.model.net.map;

import java.util.List;

/**
 * Created by dell on 2017/10/13.
 */

public class MapBean {

    private String status;
    private String info;
    private List<Map> geocodes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Map> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<Map> geocodes) {
        this.geocodes = geocodes;
    }
}
