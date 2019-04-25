package com.xmw.qiyun.data.model.net.truck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckSearchBody {

    private List<TruckSearch> UnloadSearch = new ArrayList<>();
    private String VehicleLength;
    private String VehicleTypeId;

    public List<TruckSearch> getUnloadSearch() {
        return UnloadSearch;
    }

    public void setUnloadSearch(List<TruckSearch> unloadSearch) {
        UnloadSearch = unloadSearch;
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
