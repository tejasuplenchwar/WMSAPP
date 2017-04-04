package com.example.admin.wmsapp.Activity.Model;

import java.util.List;

/**
 * Created by admin on 16/03/2017.
 */

public class InvoiceDetails {
    public List<MaterialList> materialList;

    public List<MaterialList> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialList> materialList) {
        this.materialList = materialList;
    }
}
