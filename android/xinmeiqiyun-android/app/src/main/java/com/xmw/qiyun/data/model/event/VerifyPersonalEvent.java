package com.xmw.qiyun.data.model.event;

import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VerifyPersonalEvent {

    private VerifyPersonalInfo verifyPersonalInfo;

    public VerifyPersonalInfo getVerifyPersonalInfo() {
        return verifyPersonalInfo;
    }

    public void setVerifyPersonalInfo(VerifyPersonalInfo verifyPersonalInfo) {
        this.verifyPersonalInfo = verifyPersonalInfo;
    }
}
