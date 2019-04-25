package com.xmw.qiyun.ui.cargo.cargoList;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.cargo.CargoListBean;
import com.xmw.qiyun.data.model.net.cargo.CargoSearch;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.standard.VehicleTypeBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.cargo.cargoDetail.CargoDetailActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/8/2.
 */

class CargoListPresentImpl implements CargoListContract.Presenter {

    private Context mContext;

    private CargoListContract.View mView;

    private List<Standard> mProvinceList = new ArrayList<>();
    private List<Standard> mCityList = new ArrayList<>();
    private List<Standard> mCountyList = new ArrayList<>();
    private List<Standard> mVehicleLengthList = new ArrayList<>();
    private List<Standard> mVehicleTypeList = new ArrayList<>();

    CargoListPresentImpl(Context context, CargoListContract.View view) {

        mContext = context;
        mView = view;

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mCountyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
        mVehicleLengthList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleLength(), VehicleLengthBean.class).getData());
        mVehicleTypeList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleType(), VehicleTypeBean.class).getData());
    }

    @Override
    public void bindView(CargoListContract.View view) {

    }

    @Override
    public void getData(int page, CargoSearchBody cargoSearchBody) {

        API.getService().getCargoList(page, cargoSearchBody).subscribe(new MySubscriber<CargoListBean>() {
            @Override
            public void onNext(CargoListBean cargoListBean) {

                mView.showList(cargoListBean.getData());
            }
        });
    }

    @Override
    public void getTitleData(final CargoSearchBody cargoSearchBody) {

        String startTitle = "";
        StringBuilder endTitle = new StringBuilder();
        StringBuilder searchTitle = new StringBuilder();

        switch (cargoSearchBody.getLoadRegionType()){

            default:
                break;

            case 1:{

                for(Standard standard : mProvinceList){

                    if(standard.getId().equals(cargoSearchBody.getLoadRegionId())){

                        startTitle = standard.getValue();
                        break;
                    }
                }
            }
            break;

            case 2:{

                for(Standard standard : mCityList){

                    if(standard.getId().equals(cargoSearchBody.getLoadRegionId())){

                        startTitle = standard.getValue();
                        break;
                    }
                }
            }
            break;

            case 3:{

                for(Standard standard : mCountyList){

                    if(standard.getId().equals(cargoSearchBody.getLoadRegionId())){

                        startTitle = standard.getValue();
                        break;
                    }
                }
            }
            break;
        }

        for(CargoSearch cargoSearch : cargoSearchBody.getUnloadSearch()){

            switch (cargoSearch.getRegionType()){

                default:
                    break;

                case 1:{

                    for(Standard standard : mProvinceList){

                        if(standard.getId().equals(cargoSearch.getRegionId())){

                            if(endTitle.length() == 0) {

                                endTitle.append(standard.getValue());

                            }else{

                                endTitle.append(",").append(standard.getValue());
                            }

                            break;
                        }
                    }
                }
                break;

                case 2:{

                    for(Standard standard : mCityList){

                        if(standard.getId().equals(cargoSearch.getRegionId())){

                            if(endTitle.length() == 0) {

                                endTitle.append(standard.getValue());

                            }else{

                                endTitle.append(",").append(standard.getValue());
                            }

                            break;
                        }
                    }
                }
                break;

                case 3:{

                    for(Standard standard : mCountyList){

                        if(standard.getId().equals(cargoSearch.getRegionId())){

                            if(endTitle.length() == 0) {

                                endTitle.append(standard.getValue());

                            }else{

                                endTitle.append(",").append(standard.getValue());
                            }

                            break;
                        }
                    }
                }
                break;
            }
        }



        if(!CommonUtil.isNullOrEmpty(cargoSearchBody.getVehicleLength())){

            searchTitle.append(cargoSearchBody.getVehicleLength());
        }

        for(Standard standard : mVehicleTypeList){

            if(standard.getId().equals(cargoSearchBody.getVehicleTypeId())){

                if(searchTitle.length() != 0){
                    searchTitle.append(",");
                }

                searchTitle.append(standard.getValue());

                break;
            }
        }

        mView.showTitle(startTitle, endTitle.toString(), searchTitle.toString());
    }

    @Override
    public void getLocationData(final Standard standard, final int type, final boolean isBack, final boolean isLoad, final CargoSearchBody cargoSearchBody) {

        final List<String> args = new ArrayList<>();

        for (CargoSearch cargoSearch : cargoSearchBody.getUnloadSearch()) {

            args.add(cargoSearch.getRegionId());
        }

        switch (type) {

            default:
                break;

            case 1: {

                List<LocationItem> locationItemList = new ArrayList<>();

                LocationItem locationItemFirst = new LocationItem();

                Standard standardNull = new Standard();
                standardNull.setValue("全国");

                locationItemFirst.setStandard(standardNull);

                if (isLoad) {
                    locationItemFirst.setHasSelected(CommonUtil.isNullOrEmpty(cargoSearchBody.getLoadRegionId()));
                } else {
                    locationItemFirst.setHasSelected(CommonUtil.isNullOrEmpty(cargoSearchBody.getUnloadSearch().get(0).getRegionId()));
                }

                locationItemList.add(locationItemFirst);

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();

                    locationItem.setProvinceId(mProvinceList.get(i).getId());
                    locationItem.setStandard(mProvinceList.get(i));

                    if (isLoad) {
                        locationItem.setHasSelected(mProvinceList.get(i).getId().equals(cargoSearchBody.getLoadRegionId()));
                    } else {
                        locationItem.setHasSelected(args.size() != 0 && args.contains(mProvinceList.get(i).getId()));
                    }

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        if (isLoad) {

                            mView.updateStartButton(false);
                            mView.showStartList(locationItemList, standard, type);


                        } else {

                            mView.updateEndButton(false);
                            mView.showEndList(locationItemList, standard, type);
                        }
                    }
                }
            }
            break;

            case 2: {

                final List<LocationItem> locationItemList = new ArrayList<>();

                for (Standard standardItem : mProvinceList) {

                    if (standardItem.getId().equals(isBack ? standard.getParentId() : standard.getId())) {

                        LocationItem locationItemFirst = new LocationItem();
                        locationItemFirst.setProvinceId(standardItem.getId());
                        locationItemFirst.setStandard(standardItem);

                        if (isLoad) {
                            locationItemFirst.setHasSelected(standardItem.getId().equals(cargoSearchBody.getLoadRegionId()));
                        } else {
                            locationItemFirst.setHasSelected(args.size() != 0 && args.contains(standardItem.getId()));
                        }

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

                        if (isLoad) {
                            locationItem.setHasSelected(mCityList.get(i).getId().equals(cargoSearchBody.getLoadRegionId()));
                        } else {
                            locationItem.setHasSelected(args.size() != 0 && args.contains(mCityList.get(i).getId()));
                        }

                        locationItemList.add(locationItem);
                    }

                    if (i == mCityList.size() - 1) {

                        if (isLoad) {

                            mView.updateStartButton(true);
                            mView.showStartList(locationItemList, standard, type);

                        } else {

                            mView.updateEndButton(true);
                            mView.showEndList(locationItemList, standard, type);
                        }
                    }
                }
            }
            break;

            case 3: {

                final List<LocationItem> locationItemList = new ArrayList<>();

                for (Standard standardItem : mCityList) {

                    if (standardItem.getId().equals(standard.getId())) {

                        LocationItem locationItemFirst = new LocationItem();
                        locationItemFirst.setProvinceId(standardItem.getParentId());
                        locationItemFirst.setCityId(standardItem.getId());
                        locationItemFirst.setStandard(standardItem);

                        if (isLoad) {
                            locationItemFirst.setHasSelected(standardItem.getId().equals(cargoSearchBody.getLoadRegionId()));
                        } else {
                            locationItemFirst.setHasSelected(args.size() != 0 && args.contains(standardItem.getId()));
                        }

                        locationItemList.add(locationItemFirst);
                    }
                }

                for (int i = 0; i < mCountyList.size(); i++) {

                    if (standard.getId().equals(mCountyList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setProvinceId(standard.getParentId());
                        locationItem.setCityId(mCountyList.get(i).getParentId());
                        locationItem.setCountyId(mCountyList.get(i).getId());
                        locationItem.setStandard(mCountyList.get(i));

                        if (isLoad) {
                            locationItem.setHasSelected(mCountyList.get(i).getId().equals(cargoSearchBody.getLoadRegionId()));
                        } else {
                            locationItem.setHasSelected(args.size() != 0 && args.contains(mCountyList.get(i).getId()));
                        }

                        locationItemList.add(locationItem);
                    }

                    if (i == mCountyList.size() - 1) {

                        if (isLoad) {

                            mView.updateStartButton(true);
                            mView.showStartList(locationItemList, standard, type);

                        } else {

                            mView.updateEndButton(true);
                            mView.showEndList(locationItemList, standard, type);
                        }
                    }
                }
            }
            break;
        }
    }

    @Override
    public void getVehicleData(final CargoSearchBody cargoSearchBody) {

        boolean hasMatched = false;

        List<StandardItem> vehicleLengthList = new ArrayList<>();

        Standard standardFirst = new Standard();
        standardFirst.setOptionCode("VehicleLength");
        standardFirst.setValue("不限");

        StandardItem standardItemFirst = new StandardItem();
        standardItemFirst.setStandard(standardFirst);
        standardItemFirst.setHasSelected(CommonUtil.isNullOrEmpty(cargoSearchBody.getVehicleLength()));

        if(standardItemFirst.isHasSelected()){
            hasMatched = true;
        }

        vehicleLengthList.add(standardItemFirst);

        for (int i = 0; i < mVehicleLengthList.size(); i++) {

            Standard standard = mVehicleLengthList.get(i);

            StandardItem standardItem = new StandardItem();
            standardItem.setStandard(standard);
            standardItem.setHasSelected(standard.getValue().equals(cargoSearchBody.getVehicleLength()));

            if(standardItem.isHasSelected()){
                hasMatched = true;
            }

            vehicleLengthList.add(standardItem);

            if (i == mVehicleLengthList.size() - 1) {

                if(!hasMatched){

                    StandardManager.saveCargoVehicleLength(cargoSearchBody.getVehicleLength());
                }

                Standard privateStandard = new Standard();
                privateStandard.setOptionCode("VehicleLength");
                privateStandard.setValue(StandardManager.getCargoVehicleLength());

                StandardItem standardItemPrivate = new StandardItem();
                standardItemPrivate.setStandard(privateStandard);
                standardItemPrivate.setHasSelected(!hasMatched);

                if(!CommonUtil.isNullOrEmpty(standardItemPrivate.getStandard().getValue())) vehicleLengthList.add(standardItemPrivate);

                Standard newStandard = new Standard();
                newStandard.setOptionCode("VehicleLength");
                newStandard.setValue("其他");

                StandardItem standardItemNew = new StandardItem();
                standardItemNew.setStandard(newStandard);
                standardItemNew.setHasSelected(false);

                vehicleLengthList.add(standardItemNew);
            }
        }

        List<StandardItem> vehicleTypeList = new ArrayList<>();

        Standard standardSecond = new Standard();
        standardSecond.setOptionCode("VehicleType");
        standardSecond.setValue("不限");

        StandardItem standardItemSecond = new StandardItem();
        standardItemSecond.setStandard(standardSecond);
        standardItemSecond.setHasSelected(CommonUtil.isNullOrEmpty(cargoSearchBody.getVehicleTypeId()));

        vehicleTypeList.add(standardItemSecond);

        for (int i = 0; i < mVehicleTypeList.size(); i++) {

            Standard standard = mVehicleTypeList.get(i);

            StandardItem standardItem = new StandardItem();
            standardItem.setStandard(standard);
            standardItem.setHasSelected(standard.getId().equals(cargoSearchBody.getVehicleTypeId()));

            vehicleTypeList.add(standardItem);
        }

        mView.showVehicleLengthList(vehicleLengthList);
        mView.showVehicleTypeList(vehicleTypeList);
    }

    @Override
    public void goCallOwner(String mobile) {

        DialogUtil.INSTANCE.initPhoneDialog(mContext, mobile);
    }

    @Override
    public void goCargoDetail(String id) {

        Bundle bundle = new Bundle();
        bundle.putString(CargoDetailActivity.EXTRA_CARGO_DETAIL, id);

        RouterUtil.go(RouterUtil.ROUTER_CARGO_DETAIL, mContext, bundle);
    }
}
