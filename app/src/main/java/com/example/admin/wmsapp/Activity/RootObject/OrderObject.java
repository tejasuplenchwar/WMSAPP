package com.example.admin.wmsapp.Activity.RootObject;

/**
 * Created by admin on 15/02/2017.
 */

public class OrderObject {
    public Order order ;
    public OrderDetails orderDetails;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
