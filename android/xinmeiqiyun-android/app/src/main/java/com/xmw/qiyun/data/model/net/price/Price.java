package com.xmw.qiyun.data.model.net.price;

import java.sql.Date;

/**
 * Created by hongyuan on 2017/9/18.
 */

public class Price {

    private String PriceDate;
    private String TypeCode;
    private String StartPoint;
    private String EndPoint;
    private float Chain;
    private float Freight;
    private int TruckingType;
    private String StatusName;

    public String getPriceDate() {
        return PriceDate;
    }

    public void setPriceDate(String priceDate) {
        PriceDate = priceDate;
    }

    public String getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(String typeCode) {
        TypeCode = typeCode;
    }

    public String getStartPoint() {
        return StartPoint;
    }

    public void setStartPoint(String startPoint) {
        StartPoint = startPoint;
    }

    public String getEndPoint() {
        return EndPoint;
    }

    public void setEndPoint(String endPoint) {
        EndPoint = endPoint;
    }

    public float getChain() {
        return Chain;
    }

    public void setChain(float chain) {
        Chain = chain;
    }

    public float getFreight() {
        return Freight;
    }

    public void setFreight(float freight) {
        Freight = freight;
    }

    public int getTruckingType() {
        return TruckingType;
    }

    public void setTruckingType(int truckingType) {
        TruckingType = truckingType;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }
}
