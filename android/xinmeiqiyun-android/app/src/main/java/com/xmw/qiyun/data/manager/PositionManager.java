package com.xmw.qiyun.data.manager;

/**
 * Created by hongyuan on 2017/8/10.
 */

public class PositionManager {

    public static void setPositionDetail(String positionDetail) {

        DataManager.set(DataManager.SHP_POSITION_DETAIL, positionDetail);
    }

    public static String getPositionDetail(){

        return DataManager.get(DataManager.SHP_POSITION_DETAIL, "");
    }
}
