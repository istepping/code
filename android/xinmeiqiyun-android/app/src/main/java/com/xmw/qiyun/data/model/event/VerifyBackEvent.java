package com.xmw.qiyun.data.model.event;

import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VerifyBackEvent {

    private VerifyPersonalAndCompany verifyPersonalAndCompany;

    public VerifyPersonalAndCompany getVerifyPersonalAndCompany() {
        return verifyPersonalAndCompany;
    }

    public void setVerifyPersonalAndCompany(VerifyPersonalAndCompany verifyPersonalAndCompany) {
        this.verifyPersonalAndCompany = verifyPersonalAndCompany;
    }
}
