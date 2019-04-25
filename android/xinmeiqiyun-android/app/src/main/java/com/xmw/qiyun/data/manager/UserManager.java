package com.xmw.qiyun.data.manager;

import com.xmw.qiyun.data.model.net.user.LoginAndRegister;

/**
 * Created by mac on 2017/7/27.
 */

public class UserManager {

    public static void login(LoginAndRegister loginAndRegister){

        DataManager.set(DataManager.SHP_ID, loginAndRegister.getId());
        DataManager.set(DataManager.SHP_TOKEN, loginAndRegister.getToken());
        DataManager.set(DataManager.SHP_IS_FULL, loginAndRegister.isFull());
    }

    public static void logout(){
        saveId("");
        saveToken("");
        saveIsFull(false);
    }

    public static void saveIsFirst(boolean isFirst) {

        DataManager.set(DataManager.SHP_IS_FIRST, isFirst);
    }

    public static boolean getIsFirst() {

        return DataManager.get(DataManager.SHP_IS_FIRST, true);
    }

    private static void saveId(String id) {

        DataManager.set(DataManager.SHP_ID, id);
    }

    public static String getId() {

        return DataManager.get(DataManager.SHP_ID, "");
    }

    private static void saveToken(String token){

        DataManager.set(DataManager.SHP_TOKEN, token);
    }

    public static String getToken() {
        return DataManager.get(DataManager.SHP_TOKEN, "");
    }

    public static void saveIsFull(boolean isFull) {

        DataManager.set(DataManager.SHP_IS_FULL, isFull);
    }

    public static boolean getIsFull() {

        return DataManager.get(DataManager.SHP_IS_FULL, false);
    }

    public static void savePhone(String phone){

        DataManager.set(DataManager.SHP_PHONE, phone);
    }

    public static String getPhone(){

        return DataManager.get(DataManager.SHP_PHONE, "");
    }

    public static void saveVehicle(String vehicleList){

        DataManager.set(DataManager.SHP_PUBLISH_VEHICLE, vehicleList);
    }

    public static String getVehicle(){

        return DataManager.get(DataManager.SHP_PUBLISH_VEHICLE, "");
    }

    public static void saveType(String typeList){

        DataManager.set(DataManager.SHP_PUBLISH_TYPE, typeList);
    }

    public static String getType(){

        return DataManager.get(DataManager.SHP_PUBLISH_TYPE, "");
    }
}
