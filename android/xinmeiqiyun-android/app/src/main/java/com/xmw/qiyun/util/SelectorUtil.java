package com.xmw.qiyun.util;

import com.xmw.qiyun.App;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.CommonSearchBody;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.util.manage.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/2.
 */

public class SelectorUtil {

    public static String getId(String provinceId, String cityId, String countyId) {

        if (CommonUtil.isNullOrEmpty(provinceId)) {
            return null;
        }

        if (CommonUtil.isNullOrEmpty(cityId)) {
            return provinceId;
        }

        if (CommonUtil.isNullOrEmpty(countyId)) {
            return cityId;
        }

        return countyId;
    }

    //单选数据获取
    public static List<LocationItem> getLocationList(boolean isContainNation, boolean isContainProvince, int type, String selectedId, CommonSearchBody commonSearchBody) {

        List<Standard> mProvinceList = new ArrayList<>();
        List<Standard> mCityList = new ArrayList<>();
        List<Standard> mCountyList = new ArrayList<>();
        List<LocationItem> locationItemList = new ArrayList<>();

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mCountyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());

        switch (type) {

            default:
                return null;

            case 1: {

                if (isContainNation)
                    locationItemList.add(new LocationItem(new Standard(App.getInstance().getString(R.string.cargo_nation)), CommonUtil.isNullOrEmpty(commonSearchBody.getId())));

                for (int i = 0; i < mProvinceList.size(); i++) {

                    locationItemList.add(new LocationItem(
                            mProvinceList.get(i).getId(),
                            mProvinceList.get(i).getValue(),
                            null, null, null, null,
                            mProvinceList.get(i),
                            mProvinceList.get(i).getId().equals(commonSearchBody.getProvinceId())));
                }

                return locationItemList;
            }

            case 2: {

                for (Standard standard : mProvinceList) {

                    if (standard.getId().equals(selectedId)) {

                        if(isContainProvince) {

                            locationItemList.add(new LocationItem(
                                    standard.getId(),
                                    standard.getValue(),
                                    null, null, null, null,
                                    standard,
                                    standard.getId().equals(commonSearchBody.getId())));
                        }

                        break;
                    }
                }

                for (int i = 0; i < mCityList.size(); i++) {

                    if (mCityList.get(i).getParentId().equals(selectedId)) {

                        locationItemList.add(new LocationItem(
                                commonSearchBody.getProvinceId(),
                                commonSearchBody.getProvinceValue(),
                                mCityList.get(i).getId(),
                                mCityList.get(i).getValue(),
                                null, null,
                                mCityList.get(i),
                                mCityList.get(i).getId().equals(commonSearchBody.getCityId())));
                    }
                }

                return locationItemList;
            }

            case 3: {

                for (Standard standard : mCityList) {

                    if (standard.getId().equals(selectedId)) {

                        locationItemList.add(new LocationItem(
                                commonSearchBody.getProvinceId(),
                                commonSearchBody.getProvinceValue(),
                                standard.getId(),
                                standard.getValue(),
                                null, null,
                                standard,
                                standard.getId().equals(commonSearchBody.getId())));
                    }
                }

                for (int i = 0; i < mCountyList.size(); i++) {

                    if (mCountyList.get(i).getParentId().equals(selectedId)) {

                        locationItemList.add(new LocationItem(
                                commonSearchBody.getProvinceId(),
                                commonSearchBody.getProvinceValue(),
                                commonSearchBody.getCityId(),
                                commonSearchBody.getCityValue(),
                                mCountyList.get(i).getId(),
                                mCountyList.get(i).getValue(),
                                mCountyList.get(i),
                                mCountyList.get(i).getId().equals(commonSearchBody.getCountyId())));
                    }
                }

                return locationItemList;
            }
        }
    }
}
