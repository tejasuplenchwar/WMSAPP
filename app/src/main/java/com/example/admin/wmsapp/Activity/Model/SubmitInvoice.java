package com.example.admin.wmsapp.Activity.Model;

import java.util.List;

/**
 * Created by admin on 15/03/2017.
 */

public class SubmitInvoice {
    public Invoice invoice;
    public InvoiceDetails invoiceDetails;
    public InvoiceTax invoiceTax;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceDetails getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public InvoiceTax getInvoiceTax() {
        return invoiceTax;
    }

    public void setInvoiceTax(InvoiceTax invoiceTax) {
        this.invoiceTax = invoiceTax;
    }
}
