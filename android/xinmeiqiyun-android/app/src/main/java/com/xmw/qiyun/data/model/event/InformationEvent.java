package com.xmw.qiyun.data.model.event;

import com.xmw.qiyun.data.model.net.user.UserInfo;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class InformationEvent {

    private UserInfo userInfo;

    public InformationEvent(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
