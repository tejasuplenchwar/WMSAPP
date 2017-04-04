package com.example.admin.wmsapp.Activity.RootObject;

/**
 * Created by admin on 15/02/2017.
 */

public class MaterialList {
    public int materialID ;
    public String materialName;
    public String unitOfMeasure;
    public double inQty;

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getInQty() {
        return inQty;
    }

    public void setInQty(double inQty) {
        this.inQty = inQty;
    }

    @Override
    public String toString() {
        return "MaterialList{" +
                "materialID=" + materialID +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", inQty=" + inQty +
                '}';
    }
}
