package com.xmw.qiyun.data.model.net.user;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class UserInfoBody {

    private String Name;
    private String CompanyName;
    private String ProvinceId;
    private String CityId;
    private String CountyId;
    private String Address;
    private String HeadPortraitId;
    private double Latitude;
    private double Longitude;

    public UserInfoBody(String name, String companyName, String provinceId, String cityId, String countyId, String address, String headPortraitId, double latitude, double longitude) {
        Name = name;
        CompanyName = companyName;
        ProvinceId = provinceId;
        CityId = cityId;
        CountyId = countyId;
        Address = address;
        HeadPortraitId = headPortraitId;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getHeadPortraitId() {
        return HeadPortraitId;
    }

    public void setHeadPortraitId(String headPortraitId) {
        HeadPortraitId = headPortraitId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
