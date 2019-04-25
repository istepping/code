package com.xmw.qiyun.data.model.net.map;

/**
 * Created by dell on 2017/10/13.
 */

public class MapSearchBody {

    private String Province;
    private String City;
    private String District;
    private double Longitude;
    private double Latitude;
    private String GoodsOwnerId;
    private String VehicleType;
    private String VehicleLength;

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getGoodsOwnerId() {
        return GoodsOwnerId;
    }

    public void setGoodsOwnerId(String goodsOwnerId) {
        GoodsOwnerId = goodsOwnerId;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        VehicleLength = vehicleLength;
    }
}
