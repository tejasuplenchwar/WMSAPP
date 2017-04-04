package com.example.admin.wmsapp.Activity.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.GenerateInvAdapter;
import com.example.admin.wmsapp.Activity.Adapter.InvoiceDataAdapter;
import com.example.admin.wmsapp.Activity.Model.Invoice;
import com.example.admin.wmsapp.Activity.Model.InvoiceDetails;
import com.example.admin.wmsapp.Activity.Model.InvoiceTax;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Model.MaterialList;
import com.example.admin.wmsapp.Activity.Model.SubmitInvoice;
import com.example.admin.wmsapp.Activity.Model.TaxInfo;
import com.example.admin.wmsapp.Activity.Model.TaxList;
import com.example.admin.wmsapp.Activity.Services.InvoiceAPI;
import com.example.admin.wmsapp.Activity.fragments.InviteFragment;
import com.example.admin.wmsapp.Activity.fragments.InvoiceFragment;
import com.example.admin.wmsapp.R;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class GenerateInvoice extends AppCompatActivity  implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {

    String result="",region="",taxName,validDate2;

    int taxId;
     int check=0;
    public List<TaxList> taxList=new ArrayList<>();
    public List<MaterialList> materialList=new ArrayList<>();
    List<Material> materials=new ArrayList<>();
    ListView listView;

    List<TaxInfo> taxInfoList=new ArrayList<>();

    ArrayAdapter<TaxInfo> dataAdapter;

    Spinner spinner;
    ArrayList<TaxInfo> spinnerTax=new ArrayList<>();

    GenerateInvAdapter adapter;
    Double discountedAmt=0.0,subTotal=0.00,discountAmt=0.00,taxValue,taxVatValue=0.0,taxCstValue=0.0,taxSBValue=0.0,total=0.0,a=0.0;

    TextView textBillTo,textInvoiceDate,textPO,textSubTotal,textDiscount,textVat,textVatData,textCST,
      textCSTData,textSwachhBharat,textSwachhBharatData,textTotalData,textValidDate,textview1,textView2;

    EditText editDiscount;

    ImageButton vatRemove,cstRemove,sbRemove;

    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
    String strDate= formatter.format(date);
    String validDate="";
    SimpleDateFormat formatter3=new SimpleDateFormat("dd/MM/yyyy");
    Date date1=new Date();
    String invoiceDate=formatter3.format(date1);
    List<Object[]> invoiceData=new ArrayList<>();
    List<Object[]> objects=new ArrayList<>();

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    InvoiceAPI api=retrofit.create(InvoiceAPI.class);

    LinearLayout layout;
     DecimalFormat precision = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
        //invoiceData= (List<Object[]>) getIntent().getSerializableExtra("INVOICEDATA");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        textBillTo= (TextView) findViewById(R.id.textBillTo);
       /* textInvoiceNo= (TextView) findViewById(R.id.textInvoiceNo);*/

        textInvoiceDate= (TextView) findViewById(R.id.textInvoiceDate);
        textPO= (TextView) findViewById(R.id.textPO);
        textSubTotal=(TextView) findViewById(R.id.textSubTotal);
        textDiscount=(TextView)findViewById(R.id.textDiscount);
        textview1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);

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
        editDiscount= (EditText) findViewById(R.id.editDiscount);

        spinner=(Spinner)findViewById(R.id.spinner);



        layout= (LinearLayout) findViewById(R.id.runtimeLayout);

        vatRemove= (ImageButton) findViewById(R.id.vatRemove);
        vatRemove.setVisibility(View.GONE);

        cstRemove= (ImageButton) findViewById(R.id.cstRemove);
        cstRemove.setVisibility(View.GONE);

        sbRemove= (ImageButton) findViewById(R.id.sbRemove);
        sbRemove.setVisibility(View.GONE);


        Calendar c=Calendar.getInstance();
        c.add(Calendar.MONTH,1);

        System.out.println("after one month:-"+c.getTime());
         validDate2=formatter.format(c.getTime());
        System.out.println("after one month:-"+validDate);
         textValidDate.setText(formatter3.format(c.getTime()));

        listView=(ListView)findViewById(R.id.listView);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        result=getIntent().getStringExtra("RESULT");
         createInvoice(result);

    }

    public void cancel(View v){
        finish();
    }

    public void generate(View v)  {
        String dt= validDate2+" 00:00:00.0";
        System.out.println(dt);

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        try {
         Date  dt1=  formatter1.parse(dt);
            validDate=formatter1.format(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Invoice invoice=new Invoice();
        invoice.setSequenceId(invoiceData.get(invoiceData.size()-1)[0].toString());
        invoice.setContractorId(Double.parseDouble(invoiceData.get(0)[1].toString()));
        invoice.setContractorName(invoiceData.get(0)[2].toString());
        invoice.setDiscount(discountAmt);
        invoice.setInvoiceDate(strDate);
        invoice.setValidDate(validDate);
        invoice.setTotal(Double.parseDouble(textTotalData.getText().toString()));
        invoice.setInvoiceStatus("pending");
        invoice.setPdfPath("");
        invoice.setPurchaseOrder(result);
        invoice.setDiscountPt(Double.parseDouble(editDiscount.getText().toString()));
        invoice.setOrgId(MainActivity._orgId);

        InvoiceTax invoiceTax=new InvoiceTax();
        invoiceTax.setTaxList(taxList);

        InvoiceDetails invoiceDetails=new InvoiceDetails();
        invoiceDetails.setMaterialList(materialList);

        SubmitInvoice submitInvoice=new SubmitInvoice();
        submitInvoice.setInvoice(invoice);
        submitInvoice.setInvoiceDetails(invoiceDetails);
        submitInvoice.setInvoiceTax(invoiceTax);

        Call<ResponseBody> call=api.submitInvoice(submitInvoice);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        startActivity(new Intent(this,InvoiceList.class));
    }


     public void vatRemove(View v){
        double a=0.0;
        a=Double.parseDouble(textVatData.getText().toString());
        total-=a;
        textVatData.setVisibility(View.GONE);
        textVat.setVisibility(View.GONE);
        vatRemove.setVisibility(View.GONE);

        textTotalData.setText(precision.format(total));

        for(TaxList tax:taxList){
            if(tax.taxName.equals("VAT")){
                taxList.remove(tax);
                break;
            }
        }


    }

    public void cstRemove(View v){

        double a=0.0;
        a=Double.parseDouble(textCSTData.getText().toString());
        total-=a;
        textCSTData.setVisibility(View.GONE);
        textCST.setVisibility(View.GONE);
        cstRemove.setVisibility(View.GONE);
        textTotalData.setText(precision.format(total));

        for(TaxList tax:taxList){
            if(tax.taxName.equals("CST")){
                taxList.remove(tax);
                break;
            }


        }


    }

    public void sbRemove(View v){

        double a=0.0;
        a=Double.parseDouble(textSwachhBharatData.getText().toString());
        total-=a;
        textSwachhBharatData.setVisibility(View.GONE);
        textSwachhBharat.setVisibility(View.GONE);
        sbRemove.setVisibility(View.GONE);
        textTotalData.setText(precision.format(total));

        for(TaxList tax:taxList){
            if(tax.taxName.equals("Swachh_Bharat")){
                taxList.remove(tax);
                break;
            }

        }

    }

    public void calender(View v){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog=new DatePickerDialog(this,dateSetListener,year,month,day);
        dialog.show();

    }

    DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            textValidDate.setText(String.format("%d/0%d/%d",dayOfMonth ,(monthOfYear + 1), year));
            validDate2=String.format("%d-0%d-%d",year ,(monthOfYear + 1), dayOfMonth);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createInvoice( Object orderId){
        Log.d("orderID","orders"+orderId);
        Call<List<Object[]>> call=api.createInvoice(orderId.toString());
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.body()!=null){
                objects=response.body();
                Log.d("",""+response.body());
                Log.d("Tejas","InvoiceDetail"+objects);
                getInvoicedata();}
            //* getActivity().finish();*//*
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {

            }
        });

    }

    private void getInvoicedata(){
        invoiceData.addAll(objects);

        textBillTo.setText(invoiceData.get(0)[2].toString());
        textInvoiceDate.setText(invoiceDate);
        textPO.setText(result);
        setTitle(invoiceData.get(invoiceData.size()-1)[0].toString());
       /* Log.d("Tejas","InvoiceDetail"+invoiceData);
        System.out.println("ContractorId:-"+invoiceData.get(0)[1].toString());
        System.out.println("ContractorName:-"+invoiceData.get(0)[2].toString());
        System.out.println("SequenceId:-"+invoiceData.get(invoiceData.size()-1)[0].toString());
        System.out.println("Region:-"+invoiceData.get(0)[8].toString());*/
        region=invoiceData.get(0)[8].toString();




       for(int i=0;i<(invoiceData.size()-1);i++){
            Material material=new Material();
           // material.setMaterialID(Double.parseDouble(invoiceData.get(i)[3].toString()));
            material.setMaterialName(invoiceData.get(i)[4].toString());
            material.setUnitPrice(Double.parseDouble(invoiceData.get(i)[5].toString()));
            material.setOrderQty(Double.parseDouble(invoiceData.get(i)[6].toString()));
            material.setmTotal(Double.parseDouble(invoiceData.get(i)[7].toString()));
            materials.add(material);

           MaterialList material1=new MaterialList();
           material1.setMaterialId(Double.parseDouble(invoiceData.get(i)[3].toString()));;
           material1.setMaterialName(invoiceData.get(i)[4].toString());
           material1.setmUnitPrice(Double.parseDouble(invoiceData.get(i)[5].toString()));
           material1.setOrderQty(Double.parseDouble(invoiceData.get(i)[6].toString()));
           material1.setmTotal(Double.parseDouble(invoiceData.get(i)[7].toString()));
           materialList.add(material1);

            subTotal+=Double.parseDouble(invoiceData.get(i)[7].toString());
            System.out.println("materialId:-"+invoiceData.get(i)[3].toString());
            System.out.println("materialName:-"+invoiceData.get(i)[4].toString());
            System.out.println("unitPrice:-"+invoiceData.get(i)[5].toString());
            System.out.println("orderqty:-"+invoiceData.get(i)[6].toString());
            System.out.println("mTotal:-"+invoiceData.get(i)[7].toString());
        }
        textSubTotal.setText(precision.format(subTotal));
        listView.setOnItemClickListener(this);
        adapter=new GenerateInvAdapter(this,materialList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        editDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               Double amount= Double.parseDouble(editDiscount.getText().toString());

                discountAmt=(subTotal*amount)/100;

                /* textDiscount.setText(""+(Math.round(discountAmt*100)/100));*/
                textDiscount.setText(precision.format(discountAmt));
                 spinner.setOnItemSelectedListener(GenerateInvoice.this);
                discountedAmt=subTotal-discountAmt;
                 total=discountedAmt;
                 textTotalData.setText(precision.format(total));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getTaxByRegion(region);



    }


    private void getTaxByRegion(String region){

     Call<List<TaxInfo>> call=api.getTaxByRegion(region);
        call.enqueue(new Callback<List<TaxInfo>>() {
            @Override
            public void onResponse(Call<List<TaxInfo>> call, Response<List<TaxInfo>> response) {
                if(response.body()!=null) {
                    taxInfoList = response.body();
                    showSpinnerTax();
                }
            }

            @Override
            public void onFailure(Call<List<TaxInfo>> call, Throwable t) {

            }
        });
    }

    private void showSpinnerTax(){

        TaxInfo taxInfo1=new TaxInfo();
        spinnerTax.add(taxInfo1);
        for(TaxInfo taxInfo:taxInfoList){
            spinnerTax.add(new TaxInfo(taxInfo.getTaxId(),taxInfo.getTaxName(),taxInfo.getTaxValue()));
        }
        dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerTax);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      Log.d("check","check"+check);

     if(++check>=1) {
             TaxInfo tax= (TaxInfo) parent.getSelectedItem();
             taxId=tax.getTaxId();
             taxName=tax.getTaxName();
             taxValue=tax.getTaxValue();
                 if(taxName.equals("")){
                     Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                 }
            if (taxName.equals("VAT")) {

                textVat.setVisibility(View.VISIBLE);
                vatRemove.setVisibility(View.VISIBLE);
                textVat.setText(taxName + "(" + taxValue + "%)");
                editDiscount.setEnabled(false);
                textVatData.setVisibility(View.VISIBLE);
                taxVatValue = (discountedAmt * taxValue) / 100;
                Log.d("abc","taxVat"+taxVatValue);
                textVatData.setText(precision.format(taxVatValue));


              total+=taxVatValue;
                textTotalData.setText(precision.format(total));

                TaxList taxs=new TaxList();
                taxs.setTaxName(taxName);
                taxs.setTaxAmt(taxVatValue);
                taxs.setTaxRegion(region);
                taxs.setTaxValue(taxValue);
               taxList.add(taxs);


            }

            if (taxName.equals("CST")) {
                textCST.setVisibility(View.VISIBLE);
                cstRemove.setVisibility(View.VISIBLE);
                textCST.setText(taxName + "(" + taxValue + "%)");

                textCSTData.setVisibility(View.VISIBLE);
                taxCstValue = (discountedAmt * taxValue) / 100;

                textCSTData.setText(precision.format(taxCstValue));


                total+=taxCstValue;
                TaxList taxs=new TaxList();
                taxs.setTaxName(taxName);
                taxs.setTaxAmt(taxCstValue);
                taxs.setTaxRegion(region);
                taxs.setTaxValue(taxValue);
                taxList.add(taxs);
                textTotalData.setText(precision.format(total));

            }
            if (taxName.equals("Swachh_Bharat")) {
                textSwachhBharat.setVisibility(View.VISIBLE);
                sbRemove.setVisibility(View.VISIBLE);
                textSwachhBharat.setText(taxName + "(" + taxValue + "%)");

                textSwachhBharatData.setVisibility(View.VISIBLE);
                taxSBValue = (discountedAmt * taxValue )/ 100;

                textSwachhBharatData.setText(precision.format(taxSBValue));

               total+=taxSBValue;
                TaxList taxs=new TaxList();
                taxs.setTaxName(taxName);
                taxs.setTaxAmt(taxSBValue);
                taxs.setTaxRegion(region);
                taxs.setTaxValue(taxValue);
                taxList.add(taxs);
                textTotalData.setText(precision.format(total));

            }



        }

        Toast.makeText(this, "check"+check, Toast.LENGTH_SHORT).show();
        Log.d("totalcs","total"+total);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MaterialList material=materialList.get(position);
        System.out.println(material.getmTotal());
        subTotal=subTotal-material.getmTotal();
        materialList.remove(material);
        adapter.notifyDataSetChanged();
        textSubTotal.setText(precision.format(subTotal));
    }
}
