package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by admin on 08/02/2017.
 */

public class Inward implements Serializable {
    private int inId;
    private long inDate;
    private Warehouse warehouse;

    public int getInId() {
        return inId;
    }

    public void setInId(int inId) {
        this.inId = inId;
    }

    public long getInDate() {
        return inDate;
    }

    public void setInDate(long inDate) {
        this.inDate = inDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
