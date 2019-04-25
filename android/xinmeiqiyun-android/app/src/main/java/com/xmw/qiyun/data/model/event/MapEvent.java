package com.xmw.qiyun.data.model.event;

/**
 * Created by dell on 2017/11/3.
 */

public class MapEvent {

    private double latitude;
    private double longitude;
    private String location;

    public MapEvent(double latitude, double longitude, String location) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
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
}
