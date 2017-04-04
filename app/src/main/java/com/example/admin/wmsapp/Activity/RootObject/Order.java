package com.example.admin.wmsapp.Activity.RootObject;

/**
 * Created by admin on 15/02/2017.
 */

public class Order {
    public int warehouseId;
    public int contractorID;
    public String organisationId;

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getContractorID() {
        return contractorID;
    }

    public void setContractorID(int contractorID) {
        this.contractorID = contractorID;
    }
}
