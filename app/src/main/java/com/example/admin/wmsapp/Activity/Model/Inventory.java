package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 08/02/2017.
 */

public class Inventory implements Serializable {
    private int serialId;
    private Material material;
    private Warehouse warehouse;
    private Double totalQty;
    private float availableQty;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }

    public float getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(float availableQty) {
        this.availableQty = availableQty;
    }
}
