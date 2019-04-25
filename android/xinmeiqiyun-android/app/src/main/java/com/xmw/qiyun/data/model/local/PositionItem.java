package com.xmw.qiyun.data.model.local;

/**
 * Created by dell on 2017/10/20.
 */

public class PositionItem {

    private double latitude;
    private double longitude;
    private String location;
    private String province;
    private String city;
    private String district;

    public PositionItem(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PositionItem(double latitude, double longitude, String location, String province, String city, String district) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
