package com.xmw.qiyun.data.model.net.user;

/**
 * Created by dell on 2018/3/8.
 */

public class CodeBody {

    private String Mobile;
    private String MobileCode;
    private String ValidateGraphic;

    public CodeBody(String mobile, String mobileCode, String validateGraphic) {
        Mobile = mobile;
        MobileCode = mobileCode;
        ValidateGraphic = validateGraphic;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobileCode() {
        return MobileCode;
    }

    public void setMobileCode(String mobileCode) {
        MobileCode = mobileCode;
    }

    public String getValidateGraphic() {
        return ValidateGraphic;
    }

    public void setValidateGraphic(String validateGraphic) {
        ValidateGraphic = validateGraphic;
    }
}
