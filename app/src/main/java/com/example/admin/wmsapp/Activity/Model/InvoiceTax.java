package com.example.admin.wmsapp.Activity.Model;

import java.util.List;

/**
 * Created by admin on 15/03/2017.
 */

public class InvoiceTax {
    public List<TaxList> taxList;

    public List<TaxList> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<TaxList> taxList) {
        this.taxList = taxList;
    }
}
