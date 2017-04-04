package com.example.admin.wmsapp.Activity.RootObject;

import com.example.admin.wmsapp.Activity.Model.TaxInfo;

import java.io.Serializable;

/**
 * Created by admin on 17/03/2017.
 */

public class InvoiceTax implements Serializable {

    private int invoiceTaxId;
    private TaxInfo tax;
    private int invoiceId;
    private double taxableAmt;

    public int getInvoiceTaxId() {
        return invoiceTaxId;
    }

    public void setInvoiceTaxId(int invoiceTaxId) {
        this.invoiceTaxId = invoiceTaxId;
    }

    public TaxInfo getTax() {
        return tax;
    }

    public void setTax(TaxInfo tax) {
        this.tax = tax;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getTaxableAmt() {
        return taxableAmt;
    }

    public void setTaxableAmt(double taxableAmt) {
        this.taxableAmt = taxableAmt;
    }
}
