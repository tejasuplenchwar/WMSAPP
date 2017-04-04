package com.example.admin.wmsapp.Activity.Services;



import com.example.admin.wmsapp.Activity.Model.SubmitInvoice;
import com.example.admin.wmsapp.Activity.Model.TaxInfo;
import com.example.admin.wmsapp.Activity.RootObject.Invoice;
import com.example.admin.wmsapp.Activity.RootObject.InvoiceDetails;
import com.example.admin.wmsapp.Activity.RootObject.InvoiceTax;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static android.R.attr.path;

/**
 * Created by admin on 15/03/2017.
 */

public interface InvoiceAPI {


    @GET("invoice/oDetailsByIdJsonAnd/{json}")
    Call<List<Object[]>> createInvoice(@Path("json") Object json);

    @GET("tax/taxDetailsByRegion/{tax_region}")
    Call<List<TaxInfo>> getTaxByRegion(@Path("tax_region") String tax_region);


    @POST("invoice/addInvoiceDetails")
    Call<ResponseBody> submitInvoice(@Body SubmitInvoice submitInvoice);

    @GET("invoice/viewInvoiceJson")
    Call<ArrayList<Invoice>> getAllInvoice();


    @GET("invoice/invoiceByOrgId/{orgId}")
    Call<ArrayList<Invoice>> getAllInvoice(@Path("orgId") String orgId);

    @GET("invoice/invoiceDetails/{invoiceId}")
    Call<ArrayList<InvoiceDetails>> getInvoiceDetailsById(@Path("invoiceId") int invoiceId);

    @GET("tax/taxDetailsById/{invoiceId}")
    Call<ArrayList<InvoiceTax>> getInvoiceTaxByInvId(@Path("invoiceId") int invoiceId);
}
