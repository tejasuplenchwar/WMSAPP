package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 14/02/2017.
 */

public class InwardList  {

    private int materialId;
    private String unitOfMeasure;
    private double quantity;



    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "InwardList{" +
                "materialId=" + materialId +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
