package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 03/02/2017.
 */

public class Warehouse implements Serializable {

    private int warehouseId;
    private String warehouseName;
    private String warehouseLoc;
    private String wRegion;



    private String organisationId;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseLoc() {
        return warehouseLoc;
    }

    public void setWarehouseLoc(String warehouseLoc) {
        this.warehouseLoc = warehouseLoc;
    }

    public String getwRegion() {
        return wRegion;
    }

    public void setwRegion(String wRegion) {
        this.wRegion = wRegion;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public Warehouse(int warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }

    public Warehouse() {
    }

    @Override
    public String toString() {
        return  warehouseName ;

    }
}
