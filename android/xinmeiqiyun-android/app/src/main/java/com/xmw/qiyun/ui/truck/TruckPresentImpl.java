package com.xmw.qiyun.ui.truck;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.standard.VehicleTypeBean;
import com.xmw.qiyun.data.model.net.truck.TruckListBean;
import com.xmw.qiyun.data.model.net.truck.TruckSearch;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.map.MapActivity;
import com.xmw.qiyun.ui.truck.truckDetail.TruckDetailActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/8/2.
 */

class TruckPresentImpl implements TruckContract.Presenter {

    private Context mContext;

    private TruckContract.View mView;

    private List<Standard> mProvinceList = new ArrayList<>();
    private List<Standard> mCityList = new ArrayList<>();
    private List<Standard> mVehicleLengthList = new ArrayList<>();
    private List<Standard> mVehicleTypeList = new ArrayList<>();

    TruckPresentImpl(Context context, TruckContract.View view){

        mContext = context;
        mView = view;

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mVehicleLengthList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleLength(), VehicleLengthBean.class).getData());
        mVehicleTypeList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleType(), VehicleTypeBean.class).getData());
    }

    @Override
    public void bindView(TruckContract.View view) {

    }

    @Override
    public void getData(int page, TruckSearchBody truckSearchBody) {

        API.getService().getTruckList(page, truckSearchBody).subscribe(new MySubscriber<TruckListBean>() {
            @Override
            public void onNext(TruckListBean truckListBean) {

                mView.showList(truckListBean.getData());
            }
        });
    }

    @Override
    public void getTitleData(final TruckSearchBody truckSearchBody) {

        StringBuilder stringBuilder = new StringBuilder();

        for(TruckSearch truckSearch : truckSearchBody.getUnloadSearch()){

            switch (truckSearch.getRegionType()){

                default:
                    break;

                case 1:{

                    for(Standard standard : mProvinceList){

                        if(standard.getId().equals(truckSearch.getRegionId())){

                            if(stringBuilder.length() == 0) {

                                stringBuilder.append(standard.getValue());

                            }else{

                                stringBuilder.append(",").append(standard.getValue());
                            }
                        }
                    }
                }
                break;

                case 2:{

                    for(Standard standard : mCityList){

                        if(standard.getId().equals(truckSearch.getRegionId())){

                            if(stringBuilder.length() == 0) {

                                stringBuilder.append(standard.getValue());

                            }else{

                                stringBuilder.append(",").append(standard.getValue());
                            }
                        }
                    }
                }
                break;
            }
        }

        StringBuilder sb = new StringBuilder();

        if(!CommonUtil.isNullOrEmpty(truckSearchBody.getVehicleLength())){

            sb.append(truckSearchBody.getVehicleLength());
        }

        for(Standard standard : mVehicleTypeList){

            if(standard.getId().equals(truckSearchBody.getVehicleTypeId())){

                if(sb.length() != 0){
                    sb.append(",");
                }

                sb.append(standard.getValue());

                break;
            }
        }

        mView.showCityTitle(stringBuilder.toString());
        mView.showSearchTitle(sb.toString());
    }

    @Override
    public void getLocationData(Standard standard, int type, boolean isBack, TruckSearchBody truckSearchBody) {

        List<String> args = new ArrayList<>();

        for(TruckSearch truckSearch : truckSearchBody.getUnloadSearch()){

            args.add(truckSearch.getRegionId());
        }

        switch (type) {

            default:
                break;

            case 1: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();

                    locationItem.setProvinceId(mProvinceList.get(i).getId());
                    locationItem.setStandard(mProvinceList.get(i));
                    locationItem.setHasSelected(args.size() != 0 && args.contains(mProvinceList.get(i).getId()));

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        mView.updateCityButton(false);
                        mView.showCityList(locationItemList, standard, type);
                    }
                }
            }
            break;

            case 2: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (Standard standardItem : mProvinceList) {

                    if (standardItem.getId().equals(isBack ? standard.getParentId() : standard.getId())) {

                        LocationItem locationItemFirst = new LocationItem();
                        locationItemFirst.setProvinceId(standardItem.getId());
                        locationItemFirst.setStandard(standardItem);
                        locationItemFirst.setHasSelected(args.size() != 0 && args.contains(standardItem.getId()));

                        locationItemList.add(locationItemFirst);

                        break;
                    }
                }

                for (int i = 0; i < mCityList.size(); i++) {

                    if ((isBack ? standard.getParentId() : standard.getId()).equals(mCityList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setProvinceId(mCityList.get(i).getParentId());
                        locationItem.setCityId(mCityList.get(i).getId());
                        locationItem.setStandard(mCityList.get(i));
                        locationItem.setHasSelected(args.size() != 0 && args.contains(mCityList.get(i).getId()));

                        locationItemList.add(locationItem);
                    }

                    if (i == mCityList.size() - 1) {

                        mView.updateCityButton(true);
                        mView.showCityList(locationItemList, standard, type);
                    }
                }
            }
            break;
        }
    }

    @Override
    public void getVehicleData(final TruckSearchBody truckSearchBody) {

        List<StandardItem> vehicleLengthList = new ArrayList<>();

        Standard standardFirst = new Standard();
        standardFirst.setOptionCode("VehicleLength");
        standardFirst.setValue("不限");

        StandardItem standardItemFirst = new StandardItem();
        standardItemFirst.setStandard(standardFirst);
        standardItemFirst.setHasSelected(CommonUtil.isNullOrEmpty(truckSearchBody.getVehicleLength()));

        vehicleLengthList.add(standardItemFirst);

        for (int i = 0; i < mVehicleLengthList.size(); i++) {

            Standard standard = mVehicleLengthList.get(i);

            StandardItem standardItem = new StandardItem();
            standardItem.setStandard(standard);
            standardItem.setHasSelected(standard.getValue().equals(truckSearchBody.getVehicleLength()));

            vehicleLengthList.add(standardItem);
        }

        mView.showVehicleLengthList(vehicleLengthList);

        List<StandardItem> vehicleTypeList = new ArrayList<>();

        Standard standardSecond = new Standard();
        standardSecond.setOptionCode("VehicleType");
        standardSecond.setValue("不限");

        StandardItem standardItemSecond = new StandardItem();
        standardItemSecond.setStandard(standardSecond);
        standardItemSecond.setHasSelected(CommonUtil.isNullOrEmpty(truckSearchBody.getVehicleTypeId()));

        vehicleTypeList.add(standardItemSecond);

        for (int i = 0; i < mVehicleTypeList.size(); i++) {

            Standard standard = mVehicleTypeList.get(i);

            StandardItem standardItem = new StandardItem();
            standardItem.setStandard(standard);
            standardItem.setHasSelected(standard.getId().equals(truckSearchBody.getVehicleTypeId()));

            vehicleTypeList.add(standardItem);
        }

        mView.showVehicleTypeList(vehicleTypeList);
    }

    @Override
    public void goCallOwner(String mobile) {

        DialogUtil.INSTANCE.initPhoneDialog(mContext, mobile);
    }

    @Override
    public void goTruckDetail(String id) {

        Bundle bundle = new Bundle();
        bundle.putString(TruckDetailActivity.EXTRA_TRUCK_DETAIL, id);

        RouterUtil.go(RouterUtil.ROUTER_TRUCK_DETAIL, mContext, bundle);
    }

    @Override
    public void goMap() {

        Bundle bundle = new Bundle();
        bundle.putInt(MapActivity.EXTRA_MAP_TYPE, MapActivity.EXTRA_MAP_TRUCK);
        bundle.putString(MapActivity.EXTRA_MAP_TITLE, mContext.getString(R.string.truck_title));

        RouterUtil.go(RouterUtil.ROUTER_MAP, mContext, bundle);
    }
}
