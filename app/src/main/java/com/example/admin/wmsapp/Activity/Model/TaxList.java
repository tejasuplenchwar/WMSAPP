package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 16/03/2017.
 */

public class TaxList {

    public String taxName ;
    public double taxValue ;
    public double taxAmt ;
    public String taxRegion ;

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

    public String getTaxRegion() {
        return taxRegion;
    }

    public void setTaxRegion(String taxRegion) {
        this.taxRegion = taxRegion;
    }
}
