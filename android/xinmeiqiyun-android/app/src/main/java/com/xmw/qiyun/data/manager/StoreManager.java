package com.xmw.qiyun.data.manager;

/**
 * Created by dell on 2018/2/6.
 */

public class StoreManager {

    public static void setOrderId(String orderId) {

        DataManager.set(DataManager.SHP_ORDER_ID, orderId);
    }

    public static String getOrderId(){

        return DataManager.get(DataManager.SHP_ORDER_ID, "");
    }

    public static void setWxId(String wxId) {

        DataManager.set(DataManager.SHP_WX_ID, wxId);
    }

    public static String getWxId(){

        return DataManager.get(DataManager.SHP_WX_ID, "");
    }
}
