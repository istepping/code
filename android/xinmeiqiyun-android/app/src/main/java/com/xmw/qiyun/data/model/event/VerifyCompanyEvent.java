package com.xmw.qiyun.data.model.event;

import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VerifyCompanyEvent {

    private VerifyCompanyInfo verifyCompanyInfo;

    public VerifyCompanyInfo getVerifyCompanyInfo() {
        return verifyCompanyInfo;
    }

    public void setVerifyCompanyInfo(VerifyCompanyInfo verifyCompanyInfo) {
        this.verifyCompanyInfo = verifyCompanyInfo;
    }
}
