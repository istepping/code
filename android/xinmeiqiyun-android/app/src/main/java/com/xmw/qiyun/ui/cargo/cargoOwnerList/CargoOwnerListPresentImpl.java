package com.xmw.qiyun.ui.cargo.cargoOwnerList;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerListBean;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerSearchBody;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.cargo.cargoOwner.CargoOwnerActivity;
import com.xmw.qiyun.ui.map.MapActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/1.
 */

class CargoOwnerListPresentImpl implements CargoOwnerListContract.Presenter {

    private Context mContext;
    private CargoOwnerListContract.View mView;

    private List<Standard> mProvinceList = new ArrayList<>();
    private List<Standard> mCityList = new ArrayList<>();
    private List<Standard> mCountyList = new ArrayList<>();

    CargoOwnerListPresentImpl(Context context, CargoOwnerListContract.View view){

        mContext = context;
        mView = view;

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mCountyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
    }

    @Override
    public void bindView(CargoOwnerListContract.View view) {

    }

    @Override
    public void getData(int page, CargoOwnerSearchBody cargoOwnerSearchBody) {

        API.getService().getCargoOwnerList(page, cargoOwnerSearchBody.getRegionType(), cargoOwnerSearchBody.getRegionId(), cargoOwnerSearchBody.getCompanyShortName()).subscribe(new MySubscriber<CargoOwnerListBean>() {
            @Override
            public void onNext(CargoOwnerListBean cargoOwnerListBean) {

                mView.showList(cargoOwnerListBean.getData());
            }
        });
    }

    @Override
    public void getTitleData(CargoOwnerSearchBody cargoOwnerSearchBody) {

        String title = "";

        switch (cargoOwnerSearchBody.getRegionType()){

            default:
                break;

            case 1:{

                if(CommonUtil.isNullOrEmpty(cargoOwnerSearchBody.getRegionId())){

                    title = mContext.getString(R.string.cargo_nation);

                }else{

                    for(Standard standard : mProvinceList){

                        if(standard.getId().equals(cargoOwnerSearchBody.getRegionId())){

                            title = standard.getValue();
                            break;
                        }
                    }
                }
            }
            break;

            case 2:{

                for(Standard standard : mCityList){

                    if(standard.getId().equals(cargoOwnerSearchBody.getRegionId())){

                        title = standard.getValue();
                        break;
                    }
                }
            }
            break;

            case 3:{

                for(Standard standard : mCountyList){

                    if(standard.getId().equals(cargoOwnerSearchBody.getRegionId())){

                        title = standard.getValue();
                        break;
                    }
                }
            }
            break;
        }

        mView.showTitle(title);
    }

    @Override
    public void getLocationData(Standard standard, int type, boolean isBack, CargoOwnerSearchBody cargoOwnerSearchBody) {

        switch (type) {

            default:
                break;

            case 1: {

                List<LocationItem> locationItemList = new ArrayList<>();

                LocationItem locationItemFirst = new LocationItem();

                Standard standardNull = new Standard();
                standardNull.setValue("全国");

                locationItemFirst.setStandard(standardNull);
                locationItemFirst.setHasSelected(CommonUtil.isNullOrEmpty(cargoOwnerSearchBody.getRegionId()));

                locationItemList.add(locationItemFirst);

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();

                    locationItem.setProvinceId(mProvinceList.get(i).getId());
                    locationItem.setStandard(mProvinceList.get(i));
                    locationItem.setHasSelected(mProvinceList.get(i).getId().equals(cargoOwnerSearchBody.getRegionId()));

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        mView.updateLocationButton(false);
                        mView.showLocationList(locationItemList, standard, type);
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
                        locationItemFirst.setHasSelected(standardItem.getId().equals(cargoOwnerSearchBody.getRegionId()));

                        locationItemList.add(locationItemFirst);

                        break;
                    }
                }

                for (int i = 0; i < mCityList.size(); i++) {

                    if ((isBack ? standard.getParentId() : standard.getId()).equals(mCityList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setProvinceId(standard.getId());
                        locationItem.setCityId(mCityList.get(i).getId());
                        locationItem.setStandard(mCityList.get(i));
                        locationItem.setHasSelected(mCityList.get(i).getId().equals(cargoOwnerSearchBody.getRegionId()));

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

                final List<LocationItem> locationItemList = new ArrayList<>();

                for (Standard standardItem : mCityList) {

                    if (standardItem.getId().equals(standard.getId())) {

                        LocationItem locationItemFirst = new LocationItem();
                        locationItemFirst.setProvinceId(standardItem.getParentId());
                        locationItemFirst.setCityId(standardItem.getId());
                        locationItemFirst.setStandard(standardItem);
                        locationItemFirst.setHasSelected(standardItem.getId().equals(cargoOwnerSearchBody.getRegionId()));

                        locationItemList.add(locationItemFirst);
                    }
                }

                for (int i = 0; i < mCountyList.size(); i++) {

                    if (standard.getId().equals(mCountyList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setProvinceId(standard.getParentId());
                        locationItem.setCityId(standard.getId());
                        locationItem.setCountyId(mCountyList.get(i).getId());
                        locationItem.setStandard(mCountyList.get(i));
                        locationItem.setHasSelected(mCountyList.get(i).getId().equals(cargoOwnerSearchBody.getRegionId()));

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
    public void goMap(CargoOwner cargoOwner) {

        Bundle bundle = new Bundle();
        bundle.putInt(MapActivity.EXTRA_MAP_TYPE, MapActivity.EXTRA_MAP_CARGO_OWNER);
        bundle.putString(MapActivity.EXTRA_MAP_TITLE, mContext.getString(R.string.cargo_owner_title));
        bundle.putString(MapActivity.EXTRA_MAP_VALUE, GsonImpl.init().toJson(cargoOwner));

        RouterUtil.go(RouterUtil.ROUTER_MAP, mContext, bundle);
    }

    @Override
    public void goCallOwner(String mobile) {

        DialogUtil.INSTANCE.initPhoneDialog(mContext, mobile);
    }

    @Override
    public void goCargoOwner(String id) {

        Bundle bundle = new Bundle();
        bundle.putString(CargoOwnerActivity.EXTRA_CARGO_OWNER, id);

        RouterUtil.go(RouterUtil.ROUTER_CARGO_OWNER, mContext, bundle);
    }
}
