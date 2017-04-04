package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 09/02/2017.
 */

public class Order implements Serializable {
    private int orderId;
    private String sequenceId;
    private long orderDate;
    private Warehouse warehouse;
    private String orderMasterInvoiceStatus;

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }

    private Contractor contractor;
    boolean box;

    public Order() {
    }



    public Order(int orderId, String sequenceId, long orderDate, Warehouse warehouse, String orderMasterInvoiceStatus, Contractor contractor, boolean box) {
        this.orderId = orderId;
        this.sequenceId = sequenceId;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.orderMasterInvoiceStatus = orderMasterInvoiceStatus;
        this.contractor = contractor;
        this.box = box;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }


    public String getOrderMasterInvoiceStatus() {
        return orderMasterInvoiceStatus;
    }

    public void setOrderMasterInvoiceStatus(String orderMasterInvoiceStatus) {
        this.orderMasterInvoiceStatus = orderMasterInvoiceStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", sequenceId='" + sequenceId + '\'' +
                ", orderDate=" + orderDate +
                ", warehouse=" + warehouse +
                ", orderMasterInvoiceStatus='" + orderMasterInvoiceStatus + '\'' +
                ", contractor=" + contractor +
                ", box=" + box +
                '}';
    }
}
