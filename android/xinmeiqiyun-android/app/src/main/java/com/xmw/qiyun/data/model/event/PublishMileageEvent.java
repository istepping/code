package com.xmw.qiyun.data.model.event;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class PublishMileageEvent {

    private int mileage;

    public PublishMileageEvent(int mileage) {
        this.mileage = mileage;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
