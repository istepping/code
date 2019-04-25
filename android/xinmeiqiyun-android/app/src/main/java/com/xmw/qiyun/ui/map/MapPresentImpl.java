package com.xmw.qiyun.ui.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.event.MapEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerListBean;
import com.xmw.qiyun.data.model.net.map.MapSearchBody;
import com.xmw.qiyun.data.model.net.map.TruckMarkerListBean;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.standard.VehicleTypeBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.truck.truckDetail.TruckDetailActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by hongyuan on 2017/10/10.
 */

class MapPresentImpl implements MapContract.Presenter {

    private Subscription mCurrentSubscription;

    private Context mContext;
    private MapContract.View mView;

    private List<Standard> mProvinceList = new ArrayList<>();
    private List<Standard> mCityList = new ArrayList<>();
    private List<Standard> mCountyList = new ArrayList<>();
    private List<Standard> mVehicleLengthList = new ArrayList<>();
    private List<Standard> mVehicleTypeList = new ArrayList<>();

    MapPresentImpl(Context context, MapContract.View view) {

        mContext = context;
        mView = view;

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mCountyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
        mVehicleLengthList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleLength(), VehicleLengthBean.class).getData());
        mVehicleTypeList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleType(), VehicleTypeBean.class).getData());
    }

    @Override
    public void bindView(MapContract.View view) {

    }

    @Override
    public void getMarkerData(MapSearchBody mapSearchBody, int type) {

        switch (type) {

            default:
                break;

            case MapActivity.EXTRA_MAP_TRUCK: {

                if (mCurrentSubscription != null) mCurrentSubscription.unsubscribe();

                mCurrentSubscription = API.getService().getTruckMarkerList(
                        mapSearchBody.getLongitude(),
                        mapSearchBody.getLatitude(),
                        mapSearchBody.getVehicleType(),
                        mapSearchBody.getVehicleLength()).subscribe(new MySubscriber<TruckMarkerListBean>() {
                    @Override
                    public void onNext(TruckMarkerListBean truckMarkerListBean) {

                        mView.showMarkers(truckMarkerListBean.getData());
                    }
                });
            }
            break;

            case MapActivity.EXTRA_MAP_CARGO:
            case MapActivity.EXTRA_MAP_CARGO_OWNER: {

                if (mCurrentSubscription != null) mCurrentSubscription.unsubscribe();

                mCurrentSubscription = API.getService().getCargoOwnerMap(
                        mapSearchBody.getLongitude(),
                        mapSearchBody.getLatitude(),
                        mapSearchBody.getGoodsOwnerId()).subscribe(new MySubscriber<CargoOwnerListBean>() {
                    @Override
                    public void onNext(CargoOwnerListBean cargoOwnerListBean) {

                        mView.showMarkers(cargoOwnerListBean.getData());
                    }
                });
            }
            break;
        }
    }

    @Override
    public void getOriginData(String code, String id) {

        API.getService().getCargoOwnerOriginMap(code, id).subscribe(new MySubscriber<CargoOwnerListBean>() {
            @Override
            public void onNext(CargoOwnerListBean cargoOwnerListBean) {

                mView.showMarkers(cargoOwnerListBean.getData());
            }
        });
    }

    @Override
    public void getTitleData(MapSearchBody mapSearchBody) {

        StringBuilder sb = new StringBuilder();

        if (!CommonUtil.isNullOrEmpty(mapSearchBody.getVehicleLength())) {

            sb.append(mapSearchBody.getVehicleLength());
        }

        for (Standard standard : mVehicleTypeList) {

            if (standard.getId().equals(mapSearchBody.getVehicleType())) {

                if (sb.length() != 0) {
                    sb.append(",");
                }

                sb.append(standard.getValue());

                break;
            }
        }

        mView.showCityTitle(CommonUtil.isNullOrEmpty(mapSearchBody.getDistrict()) ? mapSearchBody.getCity() : mapSearchBody.getDistrict());
        mView.showSearchTitle(sb.toString());
    }

    @Override
    public void getLocationData(Standard standard, int type, boolean isBack, MapSearchBody mapSearchBody) {

        switch (type) {

            default:
                break;

            case 1: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();
                    locationItem.setProvinceValue(mProvinceList.get(i).getValue());
                    locationItem.setStandard(mProvinceList.get(i));
                    locationItem.setHasSelected(mProvinceList.get(i).getValue().equals(mapSearchBody.getProvince()));

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        mView.updateLocationButton(false);
                        mView.showLocationList(locationItemList, standard, type);
                    }
                }
            }
            break;

            case 2: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mCityList.size(); i++) {

                    if ((isBack ? standard.getParentId() : standard.getId()).equals(mCityList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setCityValue(mCityList.get(i).getValue());
                        locationItem.setStandard(mCityList.get(i));
                        locationItem.setHasSelected(mCityList.get(i).getValue().equals(mapSearchBody.getCity()));

                        locationItemList.add(locationItem);
                    }

                    if (i == mCityList.size() - 1) {

                        mView.updateLocationButton(true);
                        mView.showLocationList(locationItemList, standard, type);
                    }
                }
            }
            break;

            case 3: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (Standard standardItem : mCityList) {

                    if (standardItem.getId().equals(standard.getId())) {

                        LocationItem locationItemFirst = new LocationItem();
                        locationItemFirst.setCityValue(standardItem.getValue());
                        locationItemFirst.setStandard(standardItem);
                        locationItemFirst.setHasSelected(standardItem.getValue().equals(mapSearchBody.getCity()) & CommonUtil.isNullOrEmpty(mapSearchBody.getDistrict()));

                        locationItemList.add(locationItemFirst);
                    }
                }

                for (int i = 0; i < mCountyList.size(); i++) {

                    if (standard.getId().equals(mCountyList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setCityValue(standard.getValue());
                        locationItem.setCountyValue(mCountyList.get(i).getValue());
                        locationItem.setStandard(mCountyList.get(i));
                        locationItem.setHasSelected(mCountyList.get(i).getValue().equals(mapSearchBody.getDistrict()));

                        locationItemList.add(locationItem);
                    }

                    if (i == mCountyList.size() - 1) {

                        mView.updateLocationButton(true);
                        mView.showLocationList(locationItemList, standard, type);
                    }
                }
            }
            break;
        }
    }

    @Override
    public void getVehicleData(MapSearchBody mapSearchBody) {

        List<StandardItem> vehicleLengthList = new ArrayList<>();

        Standard standardFirst = new Standard();
        standardFirst.setOptionCode("VehicleLength");
        standardFirst.setValue(mContext.getString(R.string.cargo_limit));

        StandardItem standardItemFirst = new StandardItem();
        standardItemFirst.setStandard(standardFirst);
        standardItemFirst.setHasSelected(CommonUtil.isNullOrEmpty(mapSearchBody.getVehicleLength()));

        vehicleLengthList.add(standardItemFirst);

        for (int i = 0; i < mVehicleLengthList.size(); i++) {

            Standard standard = mVehicleLengthList.get(i);

            StandardItem standardItem = new StandardItem();
            standardItem.setStandard(standard);
            standardItem.setHasSelected(standard.getValue().equals(mapSearchBody.getVehicleLength()));

            vehicleLengthList.add(standardItem);
        }

        mView.showVehicleLengthList(vehicleLengthList);

        List<StandardItem> vehicleTypeList = new ArrayList<>();

        Standard standardSecond = new Standard();
        standardSecond.setOptionCode("VehicleType");
        standardSecond.setValue(mContext.getString(R.string.cargo_limit));

        StandardItem standardItemSecond = new StandardItem();
        standardItemSecond.setStandard(standardSecond);
        standardItemSecond.setHasSelected(CommonUtil.isNullOrEmpty(mapSearchBody.getVehicleType()));

        vehicleTypeList.add(standardItemSecond);

        for (int i = 0; i < mVehicleTypeList.size(); i++) {

            Standard standard = mVehicleTypeList.get(i);

            StandardItem standardItem = new StandardItem();
            standardItem.setStandard(standard);
            standardItem.setHasSelected(standard.getId().equals(mapSearchBody.getVehicleType()));

            vehicleTypeList.add(standardItem);
        }

        mView.showVehicleTypeList(vehicleTypeList);
    }

    @Override
    public void goTruckDetail(String id) {

        Bundle bundle = new Bundle();
        bundle.putString(TruckDetailActivity.EXTRA_TRUCK_DETAIL, id);

        RouterUtil.go(RouterUtil.ROUTER_TRUCK_DETAIL, mContext, bundle);
    }

    @Override
    public void goContact(String mobile) {

        DialogUtil.INSTANCE.initPhoneDialog(mContext, mobile);
    }

    @Override
    public void goBack(MapEvent mapEvent) {

        EventBus.getDefault().post(mapEvent);

        ((Activity) mContext).finish();
    }
}
