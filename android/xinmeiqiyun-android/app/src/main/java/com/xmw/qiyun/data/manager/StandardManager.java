package com.xmw.qiyun.data.manager;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class StandardManager {

    public static void saveStatus(boolean hasSaved) {

        DataManager.set(DataManager.SHP_HAS_SAVED, hasSaved);
    }

    public static boolean getStatus() {

        return DataManager.get(DataManager.SHP_HAS_SAVED, false);
    }

    public static void saveVehicleLength(String string) {

        DataManager.set(DataManager.SHP_VEHICLE_LENGTH, string);
    }

    public static String getVehicleLength() {

        return DataManager.get(DataManager.SHP_VEHICLE_LENGTH, "");
    }

    public static void saveVehicleType(String string) {

        DataManager.set(DataManager.SHP_VEHICLE_TYPE, string);
    }

    public static String getVehicleType() {

        return DataManager.get(DataManager.SHP_VEHICLE_TYPE, "");
    }

    public static void saveGoodsUnit(String string) {

        DataManager.set(DataManager.SHP_GOODS_UNIT, string);
    }

    public static String getGoodsUnit() {

        return DataManager.get(DataManager.SHP_GOODS_UNIT, "");
    }

    public static void saveGoodsType(String string) {

        DataManager.set(DataManager.SHP_GOODS_TYPE, string);
    }

    public static String getGoodsType() {

        return DataManager.get(DataManager.SHP_GOODS_TYPE, "");
    }

    public static void saveAssembly(String string) {

        DataManager.set(DataManager.SHP_ASSEMBLY, string);
    }

    public static String getAssembly() {

        return DataManager.get(DataManager.SHP_ASSEMBLY, "");
    }

    public static void savePayment(String string) {

        DataManager.set(DataManager.SHP_PAYMENT, string);
    }

    public static String getPayment() {

        return DataManager.get(DataManager.SHP_PAYMENT, "");
    }

    public static void saveRemark(String string) {

        DataManager.set(DataManager.SHP_REMARK, string);
    }

    public static String getRemark() {

        return DataManager.get(DataManager.SHP_REMARK, "");
    }

    public static void saveProvince(String string) {

        DataManager.set(DataManager.SHP_PROVINCE, string);
    }

    public static String getProvince() {

        return DataManager.get(DataManager.SHP_PROVINCE, "");
    }

    public static void saveCity(String string) {

        DataManager.set(DataManager.SHP_CITY, string);
    }

    public static String getCity() {

        return DataManager.get(DataManager.SHP_CITY, "");
    }

    public static void saveCounty(String string) {

        DataManager.set(DataManager.SHP_COUNTY, string);
    }

    public static String getCounty() {

        return DataManager.get(DataManager.SHP_COUNTY, "");
    }

    public static void saveCargoVehicleLength(String string) {

        DataManager.set(DataManager.SHP_CARGO_VEHICLE_LENGTH, string);
    }

    public static String getCargoVehicleLength() {

        return DataManager.get(DataManager.SHP_CARGO_VEHICLE_LENGTH, "");
    }
}
