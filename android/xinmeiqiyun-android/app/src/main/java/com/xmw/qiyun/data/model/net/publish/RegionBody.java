package com.xmw.qiyun.data.model.net.publish;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class RegionBody {

    private Region Origin;
    private Region Destination;

    public RegionBody(Region origin, Region destination) {
        Origin = origin;
        Destination = destination;
    }

    public Region getOrigin() {
        return Origin;
    }

    public void setOrigin(Region origin) {
        Origin = origin;
    }

    public Region getDestination() {
        return Destination;
    }

    public void setDestination(Region destination) {
        Destination = destination;
    }
}
