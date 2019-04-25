package com.xmw.qiyun.data.model.net.user;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class UserInfoBean extends CommonResponse {

    private UserInfo Data;

    public UserInfo getData() {
        return Data;
    }

    public void setData(UserInfo data) {
        Data = data;
    }
}
