package com.xmw.qiyun.data.model.net.other;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class Version {

    private String VersonNum;
    private String VersonDescription;
    private String VersonDate;
    private String DownloadPath;
    private String FileSize;

    public String getVersonNum() {
        return VersonNum;
    }

    public void setVersonNum(String versonNum) {
        VersonNum = versonNum;
    }

    public String getVersonDescription() {
        return VersonDescription;
    }

    public void setVersonDescription(String versonDescription) {
        VersonDescription = versonDescription;
    }

    public String getVersonDate() {
        return VersonDate;
    }

    public void setVersonDate(String versonDate) {
        VersonDate = versonDate;
    }

    public String getDownloadPath() {
        return DownloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        DownloadPath = downloadPath;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String fileSize) {
        FileSize = fileSize;
    }
}
