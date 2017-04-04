package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 15/03/2017.
 */

public class TaxInfo implements Serializable {
    private int taxId;
    private String taxName;
    private String taxRegion;
    private double taxValue;

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxRegion() {
        return taxRegion;
    }

    public void setTaxRegion(String taxRegion) {
        this.taxRegion = taxRegion;
    }

    public double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }

    public TaxInfo(int taxId, String taxRegion, String taxName, double taxValue) {
        this.taxId = taxId;
        this.taxRegion = taxRegion;
        this.taxName = taxName;
        this.taxValue = taxValue;
    }

    public TaxInfo(int taxId, String taxName, double taxValue) {
        this.taxId = taxId;
        this.taxName = taxName;
        this.taxValue = taxValue;
    }

    public TaxInfo() {
    }

    @Override
    public String toString() {
        return taxName;

    }
}
