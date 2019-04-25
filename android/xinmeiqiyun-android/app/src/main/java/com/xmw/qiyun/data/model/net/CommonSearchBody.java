package com.xmw.qiyun.data.model.net;

import android.support.annotation.Nullable;

import com.xmw.qiyun.util.manage.CommonUtil;

/**
 * Created by dell on 2017/11/16.
 */

public class CommonSearchBody {

    private String provinceId;
    private String provinceValue;
    private String cityId;
    private String cityValue;
    private String countyId;
    private String countyValue;

    public CommonSearchBody(@Nullable String provinceId,
                            @Nullable String provinceValue,
                            @Nullable String cityId,
                            @Nullable String cityValue,
                            @Nullable String countyId,
                            @Nullable String countyValue) {
        this.provinceId = provinceId;
        this.provinceValue = provinceValue;
        this.cityId = cityId;
        this.cityValue = cityValue;
        this.countyId = countyId;
        this.countyValue = countyValue;
    }

    public String getId(){

        if(!CommonUtil.isNullOrEmpty(countyId)){

            return countyId;
        }

        if(!CommonUtil.isNullOrEmpty(cityId)){

            return cityId;
        }

        if(!CommonUtil.isNullOrEmpty(provinceId)){

            return provinceId;
        }

        return null;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceValue() {
        return provinceValue;
    }

    public void setProvinceValue(String provinceValue) {
        this.provinceValue = provinceValue;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityValue() {
        return cityValue;
    }

    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyValue() {
        return countyValue;
    }

    public void setCountyValue(String countyValue) {
        this.countyValue = countyValue;
    }
}
