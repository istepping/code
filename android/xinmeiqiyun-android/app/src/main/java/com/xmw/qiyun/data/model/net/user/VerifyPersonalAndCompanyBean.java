package com.xmw.qiyun.data.model.net.user;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VerifyPersonalAndCompanyBean extends CommonResponse {

    private VerifyPersonalAndCompany Data;

    public VerifyPersonalAndCompany getData() {
        return Data;
    }

    public void setData(VerifyPersonalAndCompany data) {
        Data = data;
    }
}
