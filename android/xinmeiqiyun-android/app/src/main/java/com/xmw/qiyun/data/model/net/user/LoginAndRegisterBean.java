package com.xmw.qiyun.data.model.net.user;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class LoginAndRegisterBean extends CommonResponse {

    private LoginAndRegister Data;

    public LoginAndRegister getData() {
        return Data;
    }

    public void setData(LoginAndRegister data) {
        Data = data;
    }
}
