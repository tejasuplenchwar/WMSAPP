package com.example.admin.wmsapp.Activity.RootObject;

import java.util.List;

/**
 * Created by admin on 15/02/2017.
 */

public class OrderDetails {
    public List<OrderList> orderList;

    public List<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderList> orderList) {
        this.orderList = orderList;
    }
}
