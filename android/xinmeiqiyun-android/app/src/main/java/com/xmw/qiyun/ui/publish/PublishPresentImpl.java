package com.xmw.qiyun.ui.publish;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.event.PublishMileageEvent;
import com.xmw.qiyun.data.model.event.PublishStatusEvent;
import com.xmw.qiyun.data.model.event.PublishStreamByEvent;
import com.xmw.qiyun.data.model.event.PublishStreamInEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.other.StatusBean;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.publish.PublishBodyListBean;
import com.xmw.qiyun.data.model.net.publish.PublishNotCity;
import com.xmw.qiyun.data.model.net.publish.Region;
import com.xmw.qiyun.data.model.net.publish.RegionBody;
import com.xmw.qiyun.data.model.net.publish.RegionResultBean;
import com.xmw.qiyun.data.model.net.standard.AssemblyBean;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.GoodsTypeBean;
import com.xmw.qiyun.data.model.net.standard.GoodsUnitBean;
import com.xmw.qiyun.data.model.net.standard.PaymentMethodBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.RemarkBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.standard.VehicleTypeBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.inputMulti.InputMultiActivity;
import com.xmw.qiyun.ui.inputSingle.InputSingleActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/8/3.
 */

class PublishPresentImpl implements PublishContract.Presenter {

    private Context mContext;

    private PublishContract.View mView;

    private List<Standard> mProvinceList = new ArrayList<>();
    private List<Standard> mCityList = new ArrayList<>();
    private List<Standard> mCountyList = new ArrayList<>();
    private List<Standard> mVehicleLengthList = new ArrayList<>();
    private List<Standard> mVehicleTypeList = new ArrayList<>();
    private List<Standard> mGoodsUnitList = new ArrayList<>();
    private List<Standard> mGoodsTypeList = new ArrayList<>();
    private List<Standard> mAssemblyList = new ArrayList<>();
    private List<Standard> mPaymentList = new ArrayList<>();
    private List<Standard> mRemarkList = new ArrayList<>();

    private static List<Standard> mResendCountList = new ArrayList<>();
    private static List<Standard> mResendTimeList = new ArrayList<>();

    static{

        Standard standard1 = new Standard();
        standard1.setId("5");
        standard1.setValue("5次");
        standard1.setOptionCode("ResendCount");

        Standard standard2 = new Standard();
        standard2.setId("10");
        standard2.setValue("10次");
        standard2.setOptionCode("ResendCount");

        Standard standard3 = new Standard();
        standard3.setId("15");
        standard3.setValue("15次");
        standard3.setOptionCode("ResendCount");

        mResendCountList.add(standard1);
        mResendCountList.add(standard2);
        mResendCountList.add(standard3);

        Standard standard4 = new Standard();
        standard4.setId("20");
        standard4.setValue("20分钟");
        standard4.setOptionCode("ResendTime");

        Standard standard5 = new Standard();
        standard5.setId("30");
        standard5.setValue("30分钟");
        standard5.setOptionCode("ResendTime");

        Standard standard6 = new Standard();
        standard6.setId("60");
        standard6.setValue("60分钟");
        standard6.setOptionCode("ResendTime");

        mResendTimeList.add(standard4);
        mResendTimeList.add(standard5);
        mResendTimeList.add(standard6);
    }

    PublishPresentImpl(Context context, PublishContract.View view){

        mContext = context;
        mView = view;

        mProvinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        mCityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        mCountyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
        mVehicleLengthList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleLength(), VehicleLengthBean.class).getData());
        mVehicleTypeList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleType(), VehicleTypeBean.class).getData());
        mGoodsUnitList.addAll(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData());
        mGoodsTypeList.addAll(GsonImpl.init().toObject(StandardManager.getGoodsType(), GoodsTypeBean.class).getData());
        mAssemblyList.addAll(GsonImpl.init().toObject(StandardManager.getAssembly(), AssemblyBean.class).getData());
        mPaymentList.addAll(GsonImpl.init().toObject(StandardManager.getPayment(), PaymentMethodBean.class).getData());
        mRemarkList.addAll(GsonImpl.init().toObject(StandardManager.getRemark(), RemarkBean.class).getData());
    }

    @Override
    public void bindView(PublishContract.View view) {

    }

    @Override
    public void getStatus() {

        NoteUtil.showLoading(mContext);

        API.getService().getStatus().subscribe(new MySubscriber<StatusBean>() {
            @Override
            public void onNext(StatusBean statusBean) {

                NoteUtil.hideLoading();

                if(statusBean.getStatusCode() == 1) {

                    EventBus.getDefault().post(new PublishStatusEvent(statusBean.getData().getStatus() == 1));

                }else{

                    EventBus.getDefault().post(new PublishStatusEvent(true));
                }
            }
        });
    }

    @Override
    public void getMileage(PublishBody publishBody) {

        Region regionOrigin = new Region(publishBody.getLoadProvinceId(), publishBody.getLoadCityId(), publishBody.getLoadCountyId());
        Region regionDestination = new Region(publishBody.getUnloadProvinceId(), publishBody.getUnloadCityId(), publishBody.getUnloadCountyId());

        RegionBody regionBody = new RegionBody(regionOrigin, regionDestination);

        NoteUtil.showLoading(mContext);

        API.getService().getMileage(regionBody).subscribe(new MySubscriber<RegionResultBean>() {
            @Override
            public void onNext(RegionResultBean regionResultBean) {

                NoteUtil.hideLoading();

                PublishMileageEvent publishMileageEvent = new PublishMileageEvent(regionResultBean.getData().getData());

                EventBus.getDefault().post(publishMileageEvent);
            }
        });
    }

    @Override
    public void goEditStartDetail(PublishBody publishBody) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_PUBLISH_START_DETAIL);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditEndDetail(PublishBody publishBody) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_PUBLISH_END_DETAIL);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditNum(PublishBody publishBody) {

        Bundle bundle = new Bundle();

        if(publishBody.isRange()) {

            bundle.putString(InputMultiActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));

        }else{

            bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_PUBLISH_NUM);
            bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));
        }

        RouterUtil.go(publishBody.isRange() ? RouterUtil.ROUTER_INPUT_MULTI : RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditFee(PublishBody publishBody) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_PUBLISH_FEE);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditLoadFee(PublishBody publishBody) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_PUBLISH_LOAD_FEE);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditUnLoadFee(PublishBody publishBody) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_PUBLISH_UNLOAD_FEE);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(publishBody));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void getLocationMultiData(Standard standard, int type, boolean isTop, PublishBody publishBody) {

        switch (type){

            default:
                break;

            case PublishFragment.EXTRA_LOCATION_PROVINCE:{

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();

                    locationItem.setStandard(mProvinceList.get(i));
                    locationItem.setHasSelected(false);

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        mView.updateMultiBackButton(false);
                        mView.showLocationMultiList(locationItemList, standard, type);
                    }
                }
            }
            break;

            case PublishFragment.EXTRA_LOCATION_CITY:{

                List<String> cityIdList = new ArrayList<>();

                for(PublishNotCity publishNotCity : publishBody.getNotLookCitys()){

                    cityIdList.add(publishNotCity.getCityId());
                }

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mCityList.size(); i++) {

                    if(isTop){

                        if(cityIdList.contains(mCityList.get(i).getId())){

                            LocationItem locationItem = new LocationItem();

                            locationItem.setStandard(mCityList.get(i));

                            locationItemList.add(locationItem);
                        }

                        if(i == mCityList.size() - 1){

                            mView.showLocationSelectList(locationItemList);
                        }

                    }else{

                        if(mCityList.get(i).getParentId().equals(standard.getId())){

                            LocationItem locationItem = new LocationItem();

                            locationItem.setStandard(mCityList.get(i));
                            locationItem.setHasSelected(cityIdList.contains(mCityList.get(i).getId()));

                            locationItemList.add(locationItem);
                        }

                        if (i == mCityList.size() - 1) {

                            mView.updateMultiBackButton(true);
                            mView.showLocationMultiList(locationItemList, standard, type);
                        }
                    }
                }
            }
            break;
        }
    }

    @Override
    public void getLocationSingleData(Standard standard, int type, boolean isBack, boolean isLoad, PublishBody publishBody) {

        switch (type) {

            default:
                break;

            case PublishFragment.EXTRA_LOCATION_PROVINCE: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (int i = 0; i < mProvinceList.size(); i++) {

                    LocationItem locationItem = new LocationItem();
                    locationItem.setProvinceId(mProvinceList.get(i).getId());
                    locationItem.setProvinceValue(mProvinceList.get(i).getValue());
                    locationItem.setStandard(mProvinceList.get(i));
                    locationItem.setHasSelected(false);

                    locationItemList.add(locationItem);

                    if (i == mProvinceList.size() - 1) {

                        mView.updateSingleBackButton(false);
                        mView.showLocationSingleList(locationItemList, standard, type);
                    }
                }
            }
            break;

            case PublishFragment.EXTRA_LOCATION_CITY: {

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

                        mView.updateSingleBackButton(true);
                        mView.showLocationSingleList(locationItemList, standard, type);
                    }
                }
            }
            break;

            case PublishFragment.EXTRA_LOCATION_COUNTY: {

                List<LocationItem> locationItemList = new ArrayList<>();

                for (Standard standardItem : mCityList) {

                    if (standardItem.getId().equals(standard.getId())) {

                        LocationItem locationItemFirst = new LocationItem();
                        locationItemFirst.setProvinceId(standardItem.getParentId());
                        locationItemFirst.setCityId(standardItem.getId());
                        locationItemFirst.setCityValue(standardItem.getValue());
                        locationItemFirst.setStandard(standardItem);

                        if(isLoad){
                            locationItemFirst.setHasSelected(
                                    standardItem.getId().equals(publishBody.getLoadCityId()) & CommonUtil.isNullOrEmpty(publishBody.getLoadCountyId()));
                        }else{
                            locationItemFirst.setHasSelected(
                                    standardItem.getId().equals(publishBody.getUnloadCityId()) & CommonUtil.isNullOrEmpty(publishBody.getUnloadCountyId()));
                        }

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

                        if(isLoad){
                            locationItem.setHasSelected(mCountyList.get(i).getId().equals(publishBody.getLoadCountyId()));
                        }else{
                            locationItem.setHasSelected(mCountyList.get(i).getId().equals(publishBody.getUnloadCountyId()));
                        }

                        locationItemList.add(locationItem);
                    }

                    if (i == mCountyList.size() - 1) {

                        mView.updateSingleBackButton(true);
                        mView.showLocationSingleList(locationItemList, standard, type);
                    }
                }
            }
            break;
        }
    }

    @Override
    public void getOptionMultiData(final PublishBody publishBody, int type) {

        switch (type){

            default:
                break;

            case PublishFragment.EXTRA_OPTION_VEHICLE:{

                final List<String> lengthList = new ArrayList<>();

                if(!CommonUtil.isNullOrEmpty(publishBody.getVehicleLength())){

                    if(publishBody.getVehicleLength().contains(",")){

                        String[] args = publishBody.getVehicleLength().split(",");

                        for(String string : args){

                            lengthList.add(string);
                        }

                    }else{

                        lengthList.add(publishBody.getVehicleLength());
                    }
                }

                List<StandardItem> vehicleLengthList = new ArrayList<>();

                Standard standardOne = new Standard();
                StandardItem standardItemOne = new StandardItem();

                standardOne.setId("");
                standardOne.setOptionCode("VehicleLength");
                standardOne.setValue("不限");

                standardItemOne.setStandard(standardOne);
                standardItemOne.setHasSelected(CommonUtil.isNullOrEmpty(publishBody.getVehicleLength()));

                vehicleLengthList.add(standardItemOne);

                Standard standardTwo = new Standard();
                StandardItem standardItemTwo = new StandardItem();

                standardTwo.setId("");
                standardTwo.setOptionCode("VehicleLength");
                standardTwo.setValue("零担");

                standardItemTwo.setStandard(standardTwo);
                standardItemTwo.setHasSelected(lengthList.contains(standardTwo.getValue()));

                vehicleLengthList.add(standardItemTwo);

                for (int i = 0; i < mVehicleLengthList.size(); i++) {

                    Standard standard = mVehicleLengthList.get(i);

                    StandardItem standardItem1 = new StandardItem();
                    standardItem1.setStandard(standard);
                    standardItem1.setHasSelected(lengthList.contains(standard.getValue()));

                    vehicleLengthList.add(standardItem1);

                    if(i == mVehicleLengthList.size() - 1){

                        if(!CommonUtil.isNullOrEmpty(UserManager.getVehicle())){

                            for(String string : GsonImpl.init().toList(UserManager.getVehicle(), String.class)){

                                Standard standardLocal = new Standard();
                                StandardItem standardItemLocal = new StandardItem();

                                standardLocal.setId("");
                                standardLocal.setOptionCode("VehicleLength");
                                standardLocal.setValue(string);

                                standardItemLocal.setStandard(standardLocal);
                                standardItemLocal.setHasSelected(lengthList.contains(string));

                                vehicleLengthList.add(standardItemLocal);
                            }
                        }

                        Standard standardLast = new Standard();
                        StandardItem standardItemLast = new StandardItem();

                        standardLast.setId("");
                        standardLast.setOptionCode("VehicleLength");
                        standardLast.setValue("其他");

                        standardItemLast.setStandard(standardLast);
                        standardItemLast.setHasSelected(false);

                        vehicleLengthList.add(standardItemLast);
                    }
                }

                List<StandardItem> vehicleTypeList = new ArrayList<>();

                Standard standardSecond = new Standard();
                standardSecond.setId("");
                standardSecond.setOptionCode("VehicleType");
                standardSecond.setValue("不限");

                StandardItem standardItemSecond = new StandardItem();
                standardItemSecond.setStandard(standardSecond);
                standardItemSecond.setHasSelected(CommonUtil.isNullOrEmpty(publishBody.getVehicleTypeId()));

                vehicleTypeList.add(standardItemSecond);

                for (int i = 0; i < mVehicleTypeList.size(); i++) {

                    Standard standard = mVehicleTypeList.get(i);

                    StandardItem standardItem = new StandardItem();
                    standardItem.setStandard(standard);
                    standardItem.setHasSelected(standard.getId().equals(publishBody.getVehicleTypeId()));

                    vehicleTypeList.add(standardItem);
                }

                mView.showOptionMultiList(vehicleLengthList, vehicleTypeList);
            }
            break;

            case PublishFragment.EXTRA_OPTION_RESEND:{

                List<StandardItem> resendCountList = new ArrayList<>();
                List<StandardItem> resendTimeList = new ArrayList<>();

                for(Standard standard : mResendCountList){

                    StandardItem standardItem = new StandardItem();
                    standardItem.setStandard(standard);
                    standardItem.setHasSelected(publishBody.isRepeat() & Integer.valueOf(standard.getId()) == publishBody.getRepeatCount());

                    resendCountList.add(standardItem);
                }

                for(Standard standard : mResendTimeList){

                    StandardItem standardItem = new StandardItem();
                    standardItem.setStandard(standard);
                    standardItem.setHasSelected(publishBody.isRepeat() & Integer.valueOf(standard.getId()) == publishBody.getRepeatSpacingMinute());

                    resendTimeList.add(standardItem);
                }

                mView.showOptionMultiList(resendCountList, resendTimeList);
            }
            break;
        }
    }

    @Override
    public void getOptionSingleData(final PublishBody publishBody, int type) {

        switch (type){

            default:
                break;

            case PublishFragment.EXTRA_OPTION_GOODS_TYPE:{

                List<StandardItem> standardItemList = new ArrayList<>();

                for(int i = 0; i < mGoodsTypeList.size(); i++){

                    StandardItem standardItem = new StandardItem();

                    standardItem.setStandard(mGoodsTypeList.get(i));
                    standardItem.setHasSelected(mGoodsTypeList.get(i).getValue().equals(publishBody.getGoodsType()));

                    standardItemList.add(standardItem);

                    if(i == mGoodsTypeList.size() - 1){

                        if(!CommonUtil.isNullOrEmpty(UserManager.getType())){

                            for(String string : GsonImpl.init().toList(UserManager.getType(), String.class)){

                                Standard standardLocal = new Standard();
                                StandardItem standardItemLocal = new StandardItem();

                                standardLocal.setId("");
                                standardLocal.setOptionCode("GoodsType");
                                standardLocal.setValue(string);

                                standardItemLocal.setStandard(standardLocal);
                                standardItemLocal.setHasSelected(string.equals(publishBody.getGoodsType()));

                                standardItemList.add(standardItemLocal);
                            }
                        }

                        Standard standardLast = new Standard();
                        StandardItem standardItemLast = new StandardItem();

                        standardLast.setId("");
                        standardLast.setOptionCode("GoodsType");
                        standardLast.setValue("其他");

                        standardItemLast.setStandard(standardLast);
                        standardItemLast.setHasSelected(false);

                        standardItemList.add(standardItemLast);
                    }
                }

                mView.showOptionSingleList(standardItemList);
            }
            break;

            case PublishFragment.EXTRA_OPTION_ASSEMBLY:{

                List<StandardItem> standardItemList = new ArrayList<>();

                for(int i = 0; i < mAssemblyList.size(); i++){

                    StandardItem standardItem = new StandardItem();

                    standardItem.setStandard(mAssemblyList.get(i));
                    standardItem.setHasSelected(mAssemblyList.get(i).getId().equals(publishBody.getAssemblyWayId()));

                    standardItemList.add(standardItem);
                }

                mView.showOptionSingleList(standardItemList);
            }
            break;

            case PublishFragment.EXTRA_OPTION_PAYMENT_METHOD:{

                List<StandardItem> standardItemList = new ArrayList<>();

                for(int i = 0; i < mPaymentList.size(); i++){

                    StandardItem standardItem = new StandardItem();

                    standardItem.setStandard(mPaymentList.get(i));
                    standardItem.setHasSelected(mPaymentList.get(i).getId().equals(publishBody.getPaymentMethodId()));

                    standardItemList.add(standardItem);
                }

                mView.showOptionSingleList(standardItemList);
            }
            break;

            case PublishFragment.EXTRA_OPTION_REMARK:{

                List<StandardItem> standardItemList = new ArrayList<>();

                for(int i = 0; i < mRemarkList.size(); i++){

                    StandardItem standardItem = new StandardItem();

                    standardItem.setStandard(mRemarkList.get(i));
                    standardItem.setHasSelected(mRemarkList.get(i).getId().equals(publishBody.getOtherRemarkId()));

                    standardItemList.add(standardItem);
                }

                mView.showOptionSingleList(standardItemList);
            }
            break;
        }
    }

    @Override
    public void getOptionSingleData(PublishBody publishBody, int type, int unitType) {

        switch (type){

            default:
                break;

            case PublishFragment.EXTRA_OPTION_GOODS_UNIT:{

                List<StandardItem> standardItemList = new ArrayList<>();

                for(int i = 0; i < mGoodsUnitList.size(); i++){

                    StandardItem standardItem = new StandardItem();

                    standardItem.setStandard(mGoodsUnitList.get(i));

                    if(unitType == 0){

                        standardItem.setHasSelected(mGoodsUnitList.get(i).getId().equals(publishBody.getGoodsUnitId()));

                    }else{

                        standardItem.setHasSelected(mGoodsUnitList.get(i).getId().equals(publishBody.getFreightUnitId()));
                    }

                    standardItemList.add(standardItem);
                }

                mView.showOptionSingleList(standardItemList, unitType);
            }
            break;
        }
    }

    @Override
    public void doPublish(final PublishBody publishBody) {

        NoteUtil.showLoading(mContext);

        API.getService().publishCargo(publishBody).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                if(commonResponse.getStatusCode() == 1){

                    EventBus.getDefault().post(new PublishStreamByEvent());
                    EventBus.getDefault().post(new PublishStreamInEvent());

                    if(publishBody.isSaveOften()){

                        mView.doRegularRefresh();
                    }

                    mView.clear();
                }
            }
        });
    }

    @Override
    public void getRegularData(int page) {

        API.getService().getPublishRegularList(page).subscribe(new MySubscriber<PublishBodyListBean>() {
            @Override
            public void onNext(PublishBodyListBean publishBodyListBean) {

                mView.showRegularList(publishBodyListBean.getData());
            }
        });
    }

    @Override
    public void doEdit(PublishBody publishBody){

        mView.editRegularData(publishBody);
    }

    @Override
    public void doSend(PublishBody publishBody) {

        NoteUtil.showLoading(mContext);

        API.getService().publishCargo(publishBody).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                if(commonResponse.getStatusCode() == 1){

                    mView.doRegularRefresh();
                }
            }
        });
    }

    @Override
    public void doDelete(PublishBody publishBody) {

        NoteUtil.showLoading(mContext);

        API.getService().deletePublishRegular(publishBody.getId()).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                if(commonResponse.getStatusCode() == 1){

                    mView.doRegularRefresh();
                }
            }
        });
    }
}
