package com.xmw.qiyun.data.model.net.user;

import com.xmw.qiyun.data.model.net.CommonResponse;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class UserInfoEditBean extends CommonResponse {

    private UserInfoEdit Data;

    public UserInfoEdit getData() {
        return Data;
    }

    public void setData(UserInfoEdit data) {
        Data = data;
    }
}
