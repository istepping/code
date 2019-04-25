package com.xmw.qiyun.data.model.net.other;

/**
 * Created by dell on 2017/10/30.
 */

public class ExceptionBody {

    private String PackageName;
    private String SystemVersion;
    private String ClientVersion;
    private String ExceptionMessage;

    public ExceptionBody(String packageName, String systemVersion, String clientVersion, String exceptionMessage) {
        PackageName = packageName;
        SystemVersion = systemVersion;
        ClientVersion = clientVersion;
        ExceptionMessage = exceptionMessage;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    public String getClientVersion() {
        return ClientVersion;
    }

    public void setClientVersion(String clientVersion) {
        ClientVersion = clientVersion;
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }
}
