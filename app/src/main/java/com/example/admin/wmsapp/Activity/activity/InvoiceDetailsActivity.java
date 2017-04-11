package com.example.admin.wmsapp.Activity.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.InvoiceDataAdapter;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Model.MaterialList;
import com.example.admin.wmsapp.Activity.RootObject.Invoice;
import com.example.admin.wmsapp.Activity.RootObject.InvoiceDetails;
import com.example.admin.wmsapp.Activity.RootObject.InvoiceTax;
import com.example.admin.wmsapp.Activity.Services.InvoiceAPI;
import com.example.admin.wmsapp.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class InvoiceDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<InvoiceDetails> invoiceDetails;
    ArrayList<Material> materials=new ArrayList<>();
    ArrayList<InvoiceTax> invoiceTaxList=new ArrayList<>();
    int invoiceId;
    ListView listView;
    InvoiceDataAdapter adapter;
    DecimalFormat precision=new DecimalFormat("0.00");
    DownloadManager downloadManager;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    InvoiceAPI api=retrofit.create(InvoiceAPI.class);


    TextView textBillTo,textInvoiceDate,textPO,textSubTotal,textDiscount,textVat,textVatData,textCST,
            textCSTData,textSwachhBharat,textSwachhBharatData,textTotalData,textBalanceDueData,textValidDate,textDiscountD;

    double subTotal=0.0;

    String sequenceId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Invoice invoice= (Invoice) getIntent().getSerializableExtra("INVOICE");
        System.out.println(invoice.toString());
        invoiceId=invoice.getInvoiceId();
        sequenceId=invoice.getSequenceId();
        setTitle("Invoice Details"+"("+invoice.getSequenceId()+")");

        textBillTo= (TextView) findViewById(R.id.textBillTo);
        textInvoiceDate= (TextView) findViewById(R.id.textInvoiceDate);
        textPO= (TextView) findViewById(R.id.textPO);
        textSubTotal=(TextView) findViewById(R.id.textSubTotal);
        textDiscount=(TextView)findViewById(R.id.textDiscount);
        textDiscountD=(TextView) findViewById(R.id.textDiscountD);

        textVat=(TextView)findViewById(R.id.textVat) ;
        textVat.setVisibility(View.GONE);
        textVatData=(TextView) findViewById(R.id.textVatData);
        textVatData.setVisibility(View.GONE);

        textCST= (TextView) findViewById(R.id.textCST);
        textCST.setVisibility(View.GONE);
        textCSTData= (TextView) findViewById(R.id.textCSTData);
        textCSTData.setVisibility(View.GONE);

        textSwachhBharat= (TextView) findViewById(R.id.textSwachhBharat);
        textSwachhBharat.setVisibility(View.GONE);
        textSwachhBharatData= (TextView) findViewById(R.id.textSwachhBharatData);
        textSwachhBharatData.setVisibility(View.GONE);
        textTotalData= (TextView) findViewById(R.id.textTotalData);

        textValidDate= (TextView) findViewById(R.id.textValidDate);

        listView= (ListView) findViewById(R.id.listView);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Date invDate=new Date(invoice.getInvoiceDate());
        Date valDate=new Date(invoice.getValidDate());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String invoiceDate= formatter.format(invDate);
        String validDate=formatter.format(valDate);

        textInvoiceDate.setText(invoiceDate);
        textValidDate.setText(validDate);
        textPO.setText(invoice.getPurchaseOrder());

        textBillTo.setText(invoice.getContractor().getCtrName());
        textDiscountD.setText("Discount("+invoice.getDiscountPercentage()+"%)");
        textDiscount.setText(precision.format(invoice.getDiscount()));
        textTotalData.setText(precision.format(invoice.getInvoiceAmtTotal()));




        getInvoiceDetailsById(invoiceId);
        getInvoiceTaxByInvId(invoiceId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void download(View v) {

        File applictionFile = new File(Environment.
                getExternalStoragePublicDirectory(Environment
                        .DIRECTORY_DOWNLOADS).getAbsolutePath(), sequenceId+".pdf");
        if (applictionFile.exists()) {
            Toast.makeText(this, "File Already Exists",
                    Toast.LENGTH_LONG).show();
        } else {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(ROOT_URL + "invoice/files/" + sequenceId);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, sequenceId+".pdf");
            Long reference = downloadManager.enqueue(request);
        }
    }


    public void cancel(View v){
        finish();

    }

    private void getInvoiceDetailsById(int invoiceId){

        Call<ArrayList<InvoiceDetails>> call=api.getInvoiceDetailsById(invoiceId);
        call.enqueue(new Callback<ArrayList<InvoiceDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<InvoiceDetails>> call, Response<ArrayList<InvoiceDetails>> response) {
                if(response.body()!=null) {
                    invoiceDetails = response.body();
                    showList();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<InvoiceDetails>> call, Throwable t) {

            }
        });


    }

    private void showList(){

        for(InvoiceDetails invoiceDetail:invoiceDetails){
            Material material=new Material();
            material.setMaterialName(invoiceDetail.getMaterial().getMaterialName());
            material.setOrderQty(invoiceDetail.getQuantity());
            material.setUnitPrice(invoiceDetail.getMaterial().getUnitPrice());
            subTotal+=invoiceDetail.getTotalAmount();
            materials.add(material);
        }
        listView.setOnItemClickListener(this);
        adapter=new InvoiceDataAdapter(this,materials);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        textSubTotal.setText(precision.format(subTotal));

        System.out.println("materialList"+materials.toString());
    }
    private void getInvoiceTaxByInvId(int invoiceid){
        Call<ArrayList<InvoiceTax>> call=api.getInvoiceTaxByInvId(invoiceid);
        call.enqueue(new Callback<ArrayList<InvoiceTax>>() {
            @Override
            public void onResponse(Call<ArrayList<InvoiceTax>> call, Response<ArrayList<InvoiceTax>> response) {
               if(response.body()!=null) {
                   invoiceTaxList = response.body();
                   showTaxList();
               }
            }

            @Override
            public void onFailure(Call<ArrayList<InvoiceTax>> call, Throwable t) {

            }
        });
    }

    private void showTaxList(){
        if(!invoiceTaxList.isEmpty()) {
            for (InvoiceTax invoiceTax : invoiceTaxList) {
                if (invoiceTax.getTax().getTaxName().equals("VAT")) {
                    textVat.setVisibility(View.VISIBLE);
                    textVatData.setVisibility(View.VISIBLE);
                    textVat.setText(invoiceTax.getTax().getTaxName() + "(" + invoiceTax.getTax().getTaxValue() + "%)");
                    textVatData.setText(precision.format(invoiceTax.getTaxableAmt()));
                }
                if (invoiceTax.getTax().getTaxName().equals("CST")) {
                    textCST.setVisibility(View.VISIBLE);
                    textCSTData.setVisibility(View.VISIBLE);
                    textCST.setText(invoiceTax.getTax().getTaxName() + "(" + invoiceTax.getTax().getTaxValue() + "%)");
                    textCSTData.setText(precision.format(invoiceTax.getTaxableAmt()));

                }
                if (invoiceTax.getTax().getTaxName().equals("Swachh_Bharat")) {
                    textSwachhBharat.setVisibility(View.VISIBLE);
                    textSwachhBharatData.setVisibility(View.VISIBLE);
                    textSwachhBharat.setText(invoiceTax.getTax().getTaxName() + "(" + invoiceTax.getTax().getTaxValue() + "%)");
                    textSwachhBharatData.setText(precision.format(invoiceTax.getTaxableAmt()));
                }

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "abcd"+materials.get(position), Toast.LENGTH_SHORT).show();
    }
}
