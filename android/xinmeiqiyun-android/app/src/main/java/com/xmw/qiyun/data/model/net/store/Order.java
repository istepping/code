package com.xmw.qiyun.data.model.net.store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/29.
 */

public class Order {

    private List<OrderDetail> OrderDetails = new ArrayList<>();

    public List<OrderDetail> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        OrderDetails = orderDetails;
    }
}
