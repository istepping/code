package com.xmw.qiyun.data.model.net.publish;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class Region {

    private String ProvinceId;
    private String CityId;
    private String CountyId;

    public Region(String provinceId, String cityId, String countyId) {
        ProvinceId = provinceId;
        CityId = cityId;
        CountyId = countyId;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCountyId() {
        return CountyId;
    }

    public void setCountyId(String countyId) {
        CountyId = countyId;
    }
}
