package com.xmw.qiyun.data.model.net.publish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/1.
 */

public class PublishBody {

    private String Id;
    private String LoadProvinceId;
    private String LoadProvince_Value;
    private String LoadCityId;
    private String LoadCity_Value;
    private String LoadCountyId;
    private String LoadCounty_Value;
    private String UnloadProvinceId;
    private String UnloadProvince_Value;
    private String UnloadCityId;
    private String UnloadCity_Value;
    private String UnloadCountyId;
    private String UnloadCounty_Value;
    private String LoadAddress;
    private String UnloadAddress;
    private String VehicleLength;
    private String VehicleTypeId;
    private String VehicleType_Value;
    private String GoodsType;
    private String GoodsType_Value;
    private float GoodsNumber;
    private float GoodsNumberMin;
    private float GoodsNumberMax;
    private boolean IsRange;
    private String GoodsUnitId;
    private String GoodsUnit_Value;
    private float Freight;
    private String FreightUnitId;
    private String FreightUnit_Value;
    private float LoadVehicleCost;
    private float UnloadVehicleCost;
    private int Mileage;
    private boolean IsRepeat;
    private int RepeatCount;
    private int RepeatSpacingMinute;
    private int RepeatedCount;
    private boolean IsSaveOften;
    private String AssemblyWayId;
    private String AssemblyWay_Value;
    private String PaymentMethodId;
    private String PaymentMethod_Value;
    private String OtherRemarkId;
    private String OtherRemark_Value;
    private String Memo;
    private List<PublishNotCity> NotLookCitys = new ArrayList<>();
    private String LoadPlace;
    private String UnloadPlace;
    private String GoodsShortInfo;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLoadProvinceId() {
        return LoadProvinceId;
    }

    public void setLoadProvinceId(String loadProvinceId) {
        LoadProvinceId = loadProvinceId;
    }

    public String getLoadProvince_Value() {
        return LoadProvince_Value;
    }

    public void setLoadProvince_Value(String loadProvince_Value) {
        LoadProvince_Value = loadProvince_Value;
    }

    public String getLoadCityId() {
        return LoadCityId;
    }

    public void setLoadCityId(String loadCityId) {
        LoadCityId = loadCityId;
    }

    public String getLoadCity_Value() {
        return LoadCity_Value;
    }

    public void setLoadCity_Value(String loadCity_Value) {
        LoadCity_Value = loadCity_Value;
    }

    public String getLoadCountyId() {
        return LoadCountyId;
    }

    public void setLoadCountyId(String loadCountyId) {
        LoadCountyId = loadCountyId;
    }

    public String getLoadCounty_Value() {
        return LoadCounty_Value;
    }

    public void setLoadCounty_Value(String loadCounty_Value) {
        LoadCounty_Value = loadCounty_Value;
    }

    public String getUnloadProvinceId() {
        return UnloadProvinceId;
    }

    public void setUnloadProvinceId(String unloadProvinceId) {
        UnloadProvinceId = unloadProvinceId;
    }

    public String getUnloadProvince_Value() {
        return UnloadProvince_Value;
    }

    public void setUnloadProvince_Value(String unloadProvince_Value) {
        UnloadProvince_Value = unloadProvince_Value;
    }

    public String getUnloadCityId() {
        return UnloadCityId;
    }

    public void setUnloadCityId(String unloadCityId) {
        UnloadCityId = unloadCityId;
    }

    public String getUnloadCity_Value() {
        return UnloadCity_Value;
    }

    public void setUnloadCity_Value(String unloadCity_Value) {
        UnloadCity_Value = unloadCity_Value;
    }

    public String getUnloadCountyId() {
        return UnloadCountyId;
    }

    public void setUnloadCountyId(String unloadCountyId) {
        UnloadCountyId = unloadCountyId;
    }

    public String getUnloadCounty_Value() {
        return UnloadCounty_Value;
    }

    public void setUnloadCounty_Value(String unloadCounty_Value) {
        UnloadCounty_Value = unloadCounty_Value;
    }

    public String getLoadAddress() {
        return LoadAddress;
    }

    public void setLoadAddress(String loadAddress) {
        LoadAddress = loadAddress;
    }

    public String getUnloadAddress() {
        return UnloadAddress;
    }

    public void setUnloadAddress(String unloadAddress) {
        UnloadAddress = unloadAddress;
    }

    public String getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        VehicleLength = vehicleLength;
    }

    public String getVehicleTypeId() {
        return VehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = vehicleTypeId;
    }

    public String getVehicleType_Value() {
        return VehicleType_Value;
    }

    public void setVehicleType_Value(String vehicleType_Value) {
        VehicleType_Value = vehicleType_Value;
    }

    public String getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(String goodsType) {
        GoodsType = goodsType;
    }

    public String getGoodsType_Value() {
        return GoodsType_Value;
    }

    public void setGoodsType_Value(String goodsType_Value) {
        GoodsType_Value = goodsType_Value;
    }

    public float getGoodsNumber() {
        return GoodsNumber;
    }

    public void setGoodsNumber(float goodsNumber) {
        GoodsNumber = goodsNumber;
    }

    public float getGoodsNumberMin() {
        return GoodsNumberMin;
    }

    public void setGoodsNumberMin(float goodsNumberMin) {
        GoodsNumberMin = goodsNumberMin;
    }

    public float getGoodsNumberMax() {
        return GoodsNumberMax;
    }

    public void setGoodsNumberMax(float goodsNumberMax) {
        GoodsNumberMax = goodsNumberMax;
    }

    public boolean isRange() {
        return IsRange;
    }

    public void setRange(boolean range) {
        IsRange = range;
    }

    public String getGoodsUnitId() {
        return GoodsUnitId;
    }

    public void setGoodsUnitId(String goodsUnitId) {
        GoodsUnitId = goodsUnitId;
    }

    public String getGoodsUnit_Value() {
        return GoodsUnit_Value;
    }

    public void setGoodsUnit_Value(String goodsUnit_Value) {
        GoodsUnit_Value = goodsUnit_Value;
    }

    public float getFreight() {
        return Freight;
    }

    public void setFreight(float freight) {
        Freight = freight;
    }

    public String getFreightUnitId() {
        return FreightUnitId;
    }

    public void setFreightUnitId(String freightUnitId) {
        FreightUnitId = freightUnitId;
    }

    public String getFreightUnit_Value() {
        return FreightUnit_Value;
    }

    public void setFreightUnit_Value(String freightUnit_Value) {
        FreightUnit_Value = freightUnit_Value;
    }

    public float getLoadVehicleCost() {
        return LoadVehicleCost;
    }

    public void setLoadVehicleCost(float loadVehicleCost) {
        LoadVehicleCost = loadVehicleCost;
    }

    public float getUnloadVehicleCost() {
        return UnloadVehicleCost;
    }

    public void setUnloadVehicleCost(float unloadVehicleCost) {
        UnloadVehicleCost = unloadVehicleCost;
    }

    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int mileage) {
        Mileage = mileage;
    }

    public boolean isRepeat() {
        return IsRepeat;
    }

    public void setRepeat(boolean repeat) {
        IsRepeat = repeat;
    }

    public int getRepeatCount() {
        return RepeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        RepeatCount = repeatCount;
    }

    public int getRepeatSpacingMinute() {
        return RepeatSpacingMinute;
    }

    public void setRepeatSpacingMinute(int repeatSpacingMinute) {
        RepeatSpacingMinute = repeatSpacingMinute;
    }

    public int getRepeatedCount() {
        return RepeatedCount;
    }

    public void setRepeatedCount(int repeatedCount) {
        RepeatedCount = repeatedCount;
    }

    public boolean isSaveOften() {
        return IsSaveOften;
    }

    public void setSaveOften(boolean saveOften) {
        IsSaveOften = saveOften;
    }

    public String getAssemblyWayId() {
        return AssemblyWayId;
    }

    public void setAssemblyWayId(String assemblyWayId) {
        AssemblyWayId = assemblyWayId;
    }

    public String getAssemblyWay_Value() {
        return AssemblyWay_Value;
    }

    public void setAssemblyWay_Value(String assemblyWay_Value) {
        AssemblyWay_Value = assemblyWay_Value;
    }

    public String getPaymentMethodId() {
        return PaymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        PaymentMethodId = paymentMethodId;
    }

    public String getPaymentMethod_Value() {
        return PaymentMethod_Value;
    }

    public void setPaymentMethod_Value(String paymentMethod_Value) {
        PaymentMethod_Value = paymentMethod_Value;
    }

    public String getOtherRemarkId() {
        return OtherRemarkId;
    }

    public void setOtherRemarkId(String otherRemarkId) {
        OtherRemarkId = otherRemarkId;
    }

    public String getOtherRemark_Value() {
        return OtherRemark_Value;
    }

    public void setOtherRemark_Value(String otherRemark_Value) {
        OtherRemark_Value = otherRemark_Value;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public List<PublishNotCity> getNotLookCitys() {
        return NotLookCitys;
    }

    public void setNotLookCitys(List<PublishNotCity> notLookCitys) {
        NotLookCitys = notLookCitys;
    }

    public String getLoadPlace() {
        return LoadPlace;
    }

    public void setLoadPlace(String loadPlace) {
        LoadPlace = loadPlace;
    }

    public String getUnloadPlace() {
        return UnloadPlace;
    }

    public void setUnloadPlace(String unloadPlace) {
        UnloadPlace = unloadPlace;
    }

    public String getGoodsShortInfo() {
        return GoodsShortInfo;
    }

    public void setGoodsShortInfo(String goodsShortInfo) {
        GoodsShortInfo = goodsShortInfo;
    }
}
