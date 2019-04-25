package com.xmw.qiyun.data.model.local;

import android.support.annotation.Nullable;

import com.xmw.qiyun.data.model.net.standard.Standard;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class LocationItem {

    private String ProvinceId;
    private String ProvinceValue;
    private String CityId;
    private String CityValue;
    private String CountyId;
    private String CountyValue;
    private Standard standard;
    private boolean hasSelected;

    public LocationItem() {
    }

    public LocationItem(Standard standard,
                        boolean hasSelected) {
        this.standard = standard;
        this.hasSelected = hasSelected;
    }

    public LocationItem(@Nullable String provinceId,
                        @Nullable String provinceValue,
                        @Nullable String cityId,
                        @Nullable String cityValue,
                        @Nullable String countyId,
                        @Nullable String countyValue,
                        @Nullable Standard standard,
                        boolean hasSelected) {
        ProvinceId = provinceId;
        ProvinceValue = provinceValue;
        CityId = cityId;
        CityValue = cityValue;
        CountyId = countyId;
        CountyValue = countyValue;
        this.standard = standard;
        this.hasSelected = hasSelected;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceValue() {
        return ProvinceValue;
    }

    public void setProvinceValue(String provinceValue) {
        ProvinceValue = provinceValue;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityValue() {
        return CityValue;
    }

    public void setCityValue(String cityValue) {
        CityValue = cityValue;
    }

    public String getCountyId() {
        return CountyId;
    }

    public void setCountyId(String countyId) {
        CountyId = countyId;
    }

    public String getCountyValue() {
        return CountyValue;
    }

    public void setCountyValue(String countyValue) {
        CountyValue = countyValue;
    }

    public boolean isHasSelected() {
        return hasSelected;
    }

    public void setHasSelected(boolean hasSelected) {
        this.hasSelected = hasSelected;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }
}
