package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 17/02/2017.
 */

public class TopOrder {
    public int serialId ;
    public float orderQty ;
    public double returnQty ;
    public long issuedDate ;
    public long returnDate ;
    public Material material;
    public Order order ;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public float getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(float orderQty) {
        this.orderQty = orderQty;
    }

    public double getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(double returnQty) {
        this.returnQty = returnQty;
    }

    public long getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(long issuedDate) {
        this.issuedDate = issuedDate;
    }

    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
