package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 16/03/2017.
 */

public class MaterialList {

    public String materialName ;
    public double materialId ;
    public double mUnitPrice ;
    public double orderQty ;
    public double mTotal ;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public double getMaterialId() {
        return materialId;
    }

    public void setMaterialId(double materialId) {
        this.materialId = materialId;
    }

    public double getmUnitPrice() {
        return mUnitPrice;
    }

    public void setmUnitPrice(double mUnitPrice) {
        this.mUnitPrice = mUnitPrice;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    @Override
    public String toString() {
        return "MaterialList{" +
                "materialName='" + materialName + '\'' +
                ", mUnitPrice=" + mUnitPrice +
                ", orderQty=" + orderQty +
                '}';
    }
}
