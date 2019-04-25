package com.xmw.qiyun.data.model.net.store;

/**
 * Created by dell on 2018/1/29.
 */

public class OrderDetail {

    private String GoodsId;
    private String ServiceId;
    private int CountOfDeal;

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public int getCountOfDeal() {
        return CountOfDeal;
    }

    public void setCountOfDeal(int countOfDeal) {
        CountOfDeal = countOfDeal;
    }
}
