package com.xmw.qiyun.ui.truck;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.truck.Truck;
import com.xmw.qiyun.data.model.net.truck.TruckList;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;

import java.util.List;

/**
 * Created by mac on 2017/8/2.
 */

public interface TruckContract {

    interface View extends BaseView<Presenter> {

        void init();
        void showList(TruckList truckList);
        void stopRefresh();

        void showCityTitle(String cityTitle);
        void showSearchTitle(String searchTitle);

        void updateCityButton(boolean showOrHide);
        void showCityList(List<LocationItem> locationItemList, Standard standard, int type);

        void showVehicleLengthList(List<StandardItem> standardItemList);
        void showVehicleTypeList(List<StandardItem> standardItemList);

        void refreshTop(TruckSearchBody truckSearchBody);
        void refreshBottom(TruckSearchBody truckSearchBody);

        void hideAllDialog(TruckSearchBody truckSearchBody);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(int page, TruckSearchBody truckSearchBody);
        void getTitleData(TruckSearchBody truckSearchBody);

        void getLocationData(Standard standard, int type, boolean isBack, TruckSearchBody truckSearchBody);
        void getVehicleData(TruckSearchBody truckSearchBody);

        void goCallOwner(String mobile);
        void goTruckDetail(String id);
        void goMap();
    }
}
