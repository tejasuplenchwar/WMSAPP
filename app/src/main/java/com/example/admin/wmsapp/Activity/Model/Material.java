package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 02/02/2017.
 */

public class Material implements Serializable {
    private int materialID;
    private String materialName;
    private String materialDesc;
    private String unitOfMeasure;
    private String materialCode;
    private String organisationId;
    private String materialImage;
    private Category category;
    private Double unitPrice;
    private Double orderQty;
    private Double mTotal;

    public Double getmTotal() {
        return mTotal;
    }

    public void setmTotal(Double mTotal) {
        this.mTotal = mTotal;
    }

    public Double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Double orderQty) {
        this.orderQty = orderQty;
    }

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

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public String getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Material(int materialID, String materialName, String unitOfMeasure) {
        this.materialID = materialID;
        this.materialName = materialName;
        this.unitOfMeasure=unitOfMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;

        Material material = (Material) o;

        if (materialID != material.materialID) return false;
        if (!materialName.equals(material.materialName)) return false;
        if (!materialDesc.equals(material.materialDesc)) return false;
        if (!unitOfMeasure.equals(material.unitOfMeasure)) return false;
        if (!materialCode.equals(material.materialCode)) return false;
        if (!organisationId.equals(material.organisationId)) return false;
        if (!category.equals(material.category)) return false;
        return unitPrice.equals(material.unitPrice);

    }

    @Override
    public int hashCode() {
        int result = materialID;
        result = 31 * result + materialName.hashCode();
        result = 31 * result + materialDesc.hashCode();
        result = 31 * result + unitOfMeasure.hashCode();
        result = 31 * result + materialCode.hashCode();
        result = 31 * result + organisationId.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + unitPrice.hashCode();
        return result;
    }

    public Material() {
    }

    @Override
    public String toString() {
        return materialName;
    }
}
