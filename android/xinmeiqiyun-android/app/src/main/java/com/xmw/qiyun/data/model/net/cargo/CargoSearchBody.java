package com.xmw.qiyun.data.model.net.cargo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoSearchBody {

    private int LoadRegionType;
    private String LoadRegionId;
    private List<CargoSearch> UnloadSearch = new ArrayList<>();
    private String VehicleLength;
    private String VehicleTypeId;

    public int getLoadRegionType() {
        return LoadRegionType;
    }

    public void setLoadRegionType(int loadRegionType) {
        LoadRegionType = loadRegionType;
    }

    public String getLoadRegionId() {
        return LoadRegionId;
    }

    public void setLoadRegionId(String loadRegionId) {
        LoadRegionId = loadRegionId;
    }

    public List<CargoSearch> getUnloadSearch() {
        return UnloadSearch;
    }

    public void setUnloadSearch(List<CargoSearch> unloadSearch) {
        UnloadSearch.addAll(unloadSearch);
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
}
