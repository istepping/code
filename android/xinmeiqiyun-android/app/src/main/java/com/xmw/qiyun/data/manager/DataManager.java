package com.xmw.qiyun.data.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xmw.qiyun.App;

/**
 * Created by mac on 2017/7/27.
 */

class DataManager {

    //定位详情
    static final String SHP_POSITION_DETAIL = "SHP_POSITION_DETAIL";
    //是否已保存服务器数据
    static final String SHP_HAS_SAVED = "SHP_HAS_SAVED";
    //车长数据
    static final String SHP_VEHICLE_LENGTH = "SHP_VEHICLE_LENGTH";
    //车型数据
    static final String SHP_VEHICLE_TYPE = "SHP_VEHICLE_TYPE";
    //类型数据
    static final String SHP_GOODS_TYPE = "SHP_GOODS_TYPE";
    //单位数据
    static final String SHP_GOODS_UNIT = "SHP_GOODS_UNIT";
    //装货数据
    static final String SHP_ASSEMBLY = "SHP_ASSEMBLY";
    //支付数据
    static final String SHP_PAYMENT = "SHP_PAYMENT";
    //备注数据
    static final String SHP_REMARK = "SHP_REMARK";
    //省数据
    static final String SHP_PROVINCE = "SHP_PROVINCE";
    //市数据
    static final String SHP_CITY = "SHP_CITY";
    //区数据
    static final String SHP_COUNTY = "SHP_COUNTY";
    //商户订单号
    static final String SHP_ORDER_ID = "SHP_ORDER_ID";
    //微信订单号
    static final String SHP_WX_ID = "SHP_WX_ID";
    //货源私有车长
    static final String SHP_CARGO_VEHICLE_LENGTH = "SHP_CARGO_VEHICLE_LENGTH";
    //发货车辆长度
    static final String SHP_PUBLISH_VEHICLE = "SHP_PUBLISH_VEHICLE";
    //发货货物类型
    static final String SHP_PUBLISH_TYPE = "SHP_PUBLISH_TYPE";
    //是否第一次进入
    static final String SHP_IS_FIRST = "SHP_IS_FIRST";
    //用户
    static final String SHP_PHONE = "SHP_PHONE";
    //用户ID
    static final String SHP_ID = "SHP_ID";
    //用户TOKEN
    static final String SHP_TOKEN = "SHP_TOKEN";
    //用户INFORMATION
    static final String SHP_IS_FULL = "SHP_IS_FULL";

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance());
    }

    /**
     * 保存字段
     */
    static void set(String key, String value) {

        getSharedPreferences().edit().putString(key, value).apply();
    }

    /**
     * 获取字段
     */
    static String get(String key, String defaultValue) {

        return getSharedPreferences().getString(key, defaultValue);
    }

    /**
     * 保存状态
     */
    static void set(String key, boolean value) {

        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * 获取状态
     */
    static boolean get(String key, boolean defaultValue) {

        return getSharedPreferences().getBoolean(key, defaultValue);
    }
}
