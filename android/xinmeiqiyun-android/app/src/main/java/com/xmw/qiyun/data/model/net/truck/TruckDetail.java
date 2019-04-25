package com.xmw.qiyun.data.model.net.truck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckDetail {

    private String Id;
    private String Mobile;
    private String Name;
    private String VehicleLengthId;
    private String VehicleTypeId;
    private String VehicleNum;
    private String HeadPortraitId;
    private String VehicleLength_Value;
    private String VehicleType_Value;
    private List<OftenCity> OftenCitys = new ArrayList<>();
    private String OftenCityName;
    private int Status;
    private String StatusName;
    private String CreatedOn;
    private String RegisterTime;
    private String VehicleInfo_VehicleLoadName;
    private String VehicleProperty;
    private String VehicleInfo_VehicleNumType;
    private String VehicleLicenseId;
    private String DriverInfo_DriverLicenseId;
    private String DriverInfo_IDCardNum;
    private String DriverInfo_PoliticalTypeId;
    private String DriverInfo_PoliticalType_Value;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVehicleLengthId() {
        return VehicleLengthId;
    }

    public void setVehicleLengthId(String vehicleLengthId) {
        VehicleLengthId = vehicleLengthId;
    }

    public String getVehicleTypeId() {
        return VehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = vehicleTypeId;
    }

    public String getVehicleNum() {
        return VehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        VehicleNum = vehicleNum;
    }

    public String getHeadPortraitId() {
        return HeadPortraitId;
    }

    public void setHeadPortraitId(String headPortraitId) {
        HeadPortraitId = headPortraitId;
    }

    public String getVehicleLength_Value() {
        return VehicleLength_Value;
    }

    public void setVehicleLength_Value(String vehicleLength_Value) {
        VehicleLength_Value = vehicleLength_Value;
    }

    public String getVehicleType_Value() {
        return VehicleType_Value;
    }

    public void setVehicleType_Value(String vehicleType_Value) {
        VehicleType_Value = vehicleType_Value;
    }

    public List<OftenCity> getOftenCitys() {
        return OftenCitys;
    }

    public void setOftenCitys(List<OftenCity> oftenCitys) {
        OftenCitys = oftenCitys;
    }

    public String getOftenCityName() {
        return OftenCityName;
    }

    public void setOftenCityName(String oftenCityName) {
        OftenCityName = oftenCityName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(String registerTime) {
        RegisterTime = registerTime;
    }

    public String getVehicleInfo_VehicleLoadName() {
        return VehicleInfo_VehicleLoadName;
    }

    public void setVehicleInfo_VehicleLoadName(String vehicleInfo_VehicleLoadName) {
        VehicleInfo_VehicleLoadName = vehicleInfo_VehicleLoadName;
    }

    public String getVehicleProperty() {
        return VehicleProperty;
    }

    public void setVehicleProperty(String vehicleProperty) {
        VehicleProperty = vehicleProperty;
    }

    public String getVehicleInfo_VehicleNumType() {
        return VehicleInfo_VehicleNumType;
    }

    public void setVehicleInfo_VehicleNumType(String vehicleInfo_VehicleNumType) {
        VehicleInfo_VehicleNumType = vehicleInfo_VehicleNumType;
    }

    public String getVehicleLicenseId() {
        return VehicleLicenseId;
    }

    public void setVehicleLicenseId(String vehicleLicenseId) {
        VehicleLicenseId = vehicleLicenseId;
    }

    public String getDriverInfo_DriverLicenseId() {
        return DriverInfo_DriverLicenseId;
    }

    public void setDriverInfo_DriverLicenseId(String driverInfo_DriverLicenseId) {
        DriverInfo_DriverLicenseId = driverInfo_DriverLicenseId;
    }

    public String getDriverInfo_IDCardNum() {
        return DriverInfo_IDCardNum;
    }

    public void setDriverInfo_IDCardNum(String driverInfo_IDCardNum) {
        DriverInfo_IDCardNum = driverInfo_IDCardNum;
    }

    public String getDriverInfo_PoliticalTypeId() {
        return DriverInfo_PoliticalTypeId;
    }

    public void setDriverInfo_PoliticalTypeId(String driverInfo_PoliticalTypeId) {
        DriverInfo_PoliticalTypeId = driverInfo_PoliticalTypeId;
    }

    public String getDriverInfo_PoliticalType_Value() {
        return DriverInfo_PoliticalType_Value;
    }

    public void setDriverInfo_PoliticalType_Value(String driverInfo_PoliticalType_Value) {
        DriverInfo_PoliticalType_Value = driverInfo_PoliticalType_Value;
    }
}