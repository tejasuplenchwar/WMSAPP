package com.example.admin.wmsapp.Activity.RootObject;

import com.example.admin.wmsapp.Activity.Model.Contractor;

import java.io.Serializable;

/**
 * Created by admin on 16/03/2017.
 */

public class Invoice  implements Serializable{
    public int invoiceId ;
    public long invoiceDate ;
    public double invoiceAmtTotal;
    public long validDate;
    public String invoiceStatus ;
    public double discount ;
    public double discountPercentage ;
    public String purchaseOrder;
    public String sequenceId ;
    public String pdfPath ;
    public Contractor contractor ;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(long invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getInvoiceAmtTotal() {
        return invoiceAmtTotal;
    }

    public void setInvoiceAmtTotal(double invoiceAmtTotal) {
        this.invoiceAmtTotal = invoiceAmtTotal;
    }

    public long getValidDate() {
        return validDate;
    }

    public void setValidDate(long validDate) {
        this.validDate = validDate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", invoiceDate=" + invoiceDate +
                ", invoiceAmtTotal=" + invoiceAmtTotal +
                ", validDate=" + validDate +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                ", discount=" + discount +
                ", discountPercentage=" + discountPercentage +
                ", purchaseOrder='" + purchaseOrder + '\'' +
                ", sequenceId='" + sequenceId + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", contractor=" + contractor +
                '}';
    }
}
