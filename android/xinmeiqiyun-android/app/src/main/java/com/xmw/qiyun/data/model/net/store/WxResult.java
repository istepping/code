package com.xmw.qiyun.data.model.net.store;

/**
 * Created by dell on 2018/2/5.
 */

public class WxResult {

    private String AppId;
    private String PartnerId;
    private String PrepayId;
    private String PackageValue;
    private String NonceStr;
    private String TimeStamp;
    private String Sign;
    private String TradeNumber;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(String partnerId) {
        PartnerId = partnerId;
    }

    public String getPrepayId() {
        return PrepayId;
    }

    public void setPrepayId(String prepayId) {
        PrepayId = prepayId;
    }

    public String getPackageValue() {
        return PackageValue;
    }

    public void setPackageValue(String packageValue) {
        PackageValue = packageValue;
    }

    public String getNonceStr() {
        return NonceStr;
    }

    public void setNonceStr(String nonceStr) {
        NonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getTradeNumber() {
        return TradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        TradeNumber = tradeNumber;
    }
}
