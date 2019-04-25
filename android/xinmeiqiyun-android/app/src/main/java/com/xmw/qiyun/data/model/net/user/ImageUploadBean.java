package com.xmw.qiyun.data.model.net.user;
import com.xmw.qiyun.data.model.net.CommonResponse;
/**
 * Created by hongyuan on 2017/8/28.
 */
public class ImageUploadBean extends CommonResponse {
    private String Data;
    public String getData() {
        return Data;
    }
    public void setData(String data) {
        Data = data;
    }
}
