package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 09/02/2017.
 */

public class InwardDetails {
    private int serialId;
    private double inQty;
    private Material material;
    private Inward inward;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public double getInQty() {
        return inQty;
    }

    public void setInQty(double inQty) {
        this.inQty = inQty;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Inward getInward() {
        return inward;
    }

    public void setInward(Inward inward) {
        this.inward = inward;
    }
}
