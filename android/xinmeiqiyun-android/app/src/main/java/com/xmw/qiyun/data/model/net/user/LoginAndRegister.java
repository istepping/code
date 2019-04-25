package com.xmw.qiyun.data.model.net.user;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class LoginAndRegister {
    private String Id;
    private String Token;
    private boolean IsFull;
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        Token = token;
    }
    public boolean isFull() {
        return IsFull;
    }
    public void setFull(boolean full) {
        IsFull = full;
    }
}
