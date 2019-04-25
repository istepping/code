package com.xmw.qiyun.ui.map;

import com.amap.api.maps.model.MultiPointItem;
import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.event.MapEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerList;
import com.xmw.qiyun.data.model.net.map.MapSearchBody;
import com.xmw.qiyun.data.model.net.map.TruckMarkerList;
import com.xmw.qiyun.data.model.net.standard.Standard;

import java.util.List;

/**
 * Created by hongyuan on 2017/10/10.
 */

public interface MapContract {

    interface View extends BaseView<Presenter> {

        void init();
        void showMapFirst();
        void showMarkers(TruckMarkerList truckMarkerList);
        void showMarkers(CargoOwnerList cargoOwnerList);

        void showCityTitle(String cityTitle);
        void showSearchTitle(String searchTitle);

        void updateLocationButton(boolean showOrHide);
        void showLocationList(List<LocationItem> locationItemList, Standard standard, int type);

        void showVehicleLengthList(List<StandardItem> standardItemList);
        void showVehicleTypeList(List<StandardItem> standardItemList);

        void updateBody(MapSearchBody mapSearchBody);
        void updateBodyOfVehicle(MapSearchBody mapSearchBody);

        void getGeo();
        void reGetGeo();

        void initWindowView(MultiPointItem multiPointItem);
        void hideWindowView();
    }

    interface Presenter extends BasePresenter<View> {

        void getMarkerData(MapSearchBody mapSearchBody, int type);
        void getOriginData(String code, String id);
        void getTitleData(MapSearchBody mapSearchBody);

        void getLocationData(Standard standard, int type, boolean isBack, MapSearchBody mapSearchBody);
        void getVehicleData(MapSearchBody mapSearchBody);

        void goTruckDetail(String id);
        void goContact(String mobile);
        void goBack(MapEvent mapEvent);
    }
}
