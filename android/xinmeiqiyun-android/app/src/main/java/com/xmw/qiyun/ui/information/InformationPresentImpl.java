package com.xmw.qiyun.ui.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nanchen.compresshelper.CompressHelper;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.user.ImageUploadBean;
import com.xmw.qiyun.data.model.net.user.UserInfo;
import com.xmw.qiyun.data.model.net.user.UserInfoBean;
import com.xmw.qiyun.data.model.net.user.UserInfoBody;
import com.xmw.qiyun.data.model.net.user.UserInfoEditBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.service.PositionService;
import com.xmw.qiyun.ui.inputSingle.InputSingleActivity;
import com.xmw.qiyun.ui.map.MapActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hongyuan on 2017/8/28.
 */

class InformationPresentImpl implements InformationContract.Presenter {

    private Context mContext;
    private InformationContract.View mView;

    private List<Standard> mProvinceList = new ArrayList<>();
    private List<Standard> mCityList = new ArrayList<>();
    private List<Standard> mCountyList = new ArrayList<>();

    InformationPresentImpl(Context context, InformationContract.View view) {

        mContext = context;
        mView = view;

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mCountyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
    }

    @Override
    public void bindView(InformationContract.View view) {

    }

    @Override
    public void getData() {

        NoteUtil.showLoading(mContext);

        API.getService().getUserInfo(UserManager.getId()).subscribe(new MySubscriber<UserInfoBean>() {
            @Override
            public void onNext(UserInfoBean userInfoBean) {

                NoteUtil.hideLoading();

                mView.getData(userInfoBean.getData());
            }
        });
    }

    @Override
    public void selectImage() {

        MultiImageSelector.create(mContext)
                .showCamera(true)
                .single()
                .start((InformationActivity) mContext, InformationActivity.REQUEST_IMAGE);
    }

    @Override
    public File modifyImage(File file) {

        return new CompressHelper.Builder(mContext).setMaxWidth(100).setMaxHeight(100).setQuality(80).build().compressToFile(file);
    }

    @Override
    public void uploadAvatar(File file) {

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (file.length() > 0) {

            requestBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

            NoteUtil.showLoading(mContext);

            API.getService().upload(requestBodyBuilder.build()).subscribe(new MySubscriber<ImageUploadBean>() {
                @Override
                public void onNext(ImageUploadBean imageUploadBean) {

                    NoteUtil.hideLoading();
                    NoteUtil.showToast(mContext, imageUploadBean.getMessage());

                    mView.getImageId(imageUploadBean.getData());
                }
            });
        }
    }

    @Override
    public void goEditName(UserInfo userInfo) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_INFO_NAME);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(userInfo));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditCompanyName(UserInfo userInfo) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_INFO_COMPANY_NAME);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(userInfo));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditCompanyLocation(UserInfo userInfo) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_INFO_COMPANY_LOCATION);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(userInfo));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void getLocationData(final Standard standard, int type, boolean isBack, UserInfo userInfo) {

        switch (type) {

            default:
                break;

            case 1: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();
                    locationItem.setProvinceId(mProvinceList.get(i).getId());
                    locationItem.setProvinceValue(mProvinceList.get(i).getValue());
                    locationItem.setStandard(mProvinceList.get(i));
                    locationItem.setHasSelected(false);

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        mView.updateBackButton(false);
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
                        locationItem.setProvinceId(mCityList.get(i).getParentId());
                        locationItem.setCityId(mCityList.get(i).getId());
                        locationItem.setCityValue(mCityList.get(i).getValue());
                        locationItem.setStandard(mCityList.get(i));
                        locationItem.setHasSelected(false);

                        locationItemList.add(locationItem);
                    }

                    if (i == mCityList.size() - 1) {

                        mView.updateBackButton(true);
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
                        locationItemFirst.setProvinceId(standardItem.getParentId());
                        locationItemFirst.setCityId(standardItem.getId());
                        locationItemFirst.setCityValue(standardItem.getValue());
                        locationItemFirst.setStandard(standardItem);
                        locationItemFirst.setHasSelected(standardItem.getId().equals(userInfo.getCityId()) & CommonUtil.isNullOrEmpty(userInfo.getCountyId()));

                        locationItemList.add(locationItemFirst);
                    }
                }

                for (int i = 0; i < mCountyList.size(); i++) {

                    if (standard.getId().equals(mCountyList.get(i).getParentId())) {

                        LocationItem locationItem = new LocationItem();
                        locationItem.setProvinceId(standard.getParentId());
                        locationItem.setCityId(standard.getId());
                        locationItem.setCityValue(standard.getValue());
                        locationItem.setCountyId(mCountyList.get(i).getId());
                        locationItem.setCountyValue(mCountyList.get(i).getValue());
                        locationItem.setStandard(mCountyList.get(i));
                        locationItem.setHasSelected(mCountyList.get(i).getId().equals(userInfo.getCountyId()));

                        locationItemList.add(locationItem);
                    }

                    if (i == mCountyList.size() - 1) {

                        mView.updateBackButton(true);
                        mView.showLocationList(locationItemList, standard, type);
                    }
                }
            }
            break;
        }
    }

    @Override
    public void doPositionService() {

        mContext.startService(new Intent(mContext, PositionService.class));
    }

    @Override
    public void doSave(UserInfoBody userInfoBody, final boolean isFromLoginAndRegister) {

        NoteUtil.showLoading(mContext);

        API.getService().editUserInfo(UserManager.getId(), userInfoBody).subscribe(new MySubscriber<UserInfoEditBean>() {
            @Override
            public void onNext(UserInfoEditBean userInfoEditBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, userInfoEditBean.getMessage());

                UserManager.saveIsFull(userInfoEditBean.getData().isFull());

                if (userInfoEditBean.getData().isFull() && isFromLoginAndRegister) {

                    RouterUtil.go(RouterUtil.ROUTER_HOME, mContext);

                    ((Activity) mContext).finish();
                }
            }
        });
    }

    @Override
    public void goMap(double latitude, double longitude) {

        Bundle bundle = new Bundle();
        bundle.putInt(MapActivity.EXTRA_MAP_TYPE, MapActivity.EXTRA_MAP_OTHER);
        bundle.putDouble(MapActivity.EXTRA_MAP_LATITUDE, latitude);
        bundle.putDouble(MapActivity.EXTRA_MAP_LONGITUDE, longitude);

        RouterUtil.go(RouterUtil.ROUTER_MAP, mContext, bundle);
    }
}
