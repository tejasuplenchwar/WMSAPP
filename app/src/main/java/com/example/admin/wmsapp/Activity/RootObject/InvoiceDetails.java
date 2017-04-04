package com.example.admin.wmsapp.Activity.RootObject;

import com.example.admin.wmsapp.Activity.Model.Material;

import java.io.Serializable;

/**
 * Created by admin on 17/03/2017.
 */

public class InvoiceDetails implements Serializable {
    private int serialId;
    private Invoice invoice;
    private Material material;
    private double quantity;
    private double totalAmount;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}