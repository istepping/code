package com.xmw.qiyun.data.model.net.user;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class LoginAndRegisterBody {

    private String Mobile;
    private String VerificationCode;

    public LoginAndRegisterBody(String mobile, String verificationCode) {

        Mobile = mobile;
        VerificationCode = verificationCode;
    }
}
