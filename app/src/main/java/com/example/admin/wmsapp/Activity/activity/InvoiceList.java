package com.example.admin.wmsapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.admin.wmsapp.Activity.Adapter.InvoiceAdapter;
import com.example.admin.wmsapp.Activity.RootObject.Invoice;
import com.example.admin.wmsapp.Activity.Services.InvoiceAPI;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class InvoiceList extends AppCompatActivity implements  AdapterView.OnItemClickListener,SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    ListView listView;
    ArrayList<Invoice> invoiceList;
    ArrayList<Invoice> mAllData=new ArrayList<>();
    InvoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_list);
        setTitle("Invoice");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        listView= (ListView) findViewById(R.id.listView);
        getAllInvoice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView =(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
           startActivity(new Intent(this,MainActivity.class));
        }
        int id = item.getItemId();

        if(id==R.id.menuSearch){

            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllInvoice(){
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvoiceAPI api=retrofit.create(InvoiceAPI.class);

        Call<ArrayList<Invoice>> call=api.getAllInvoice(MainActivity._orgId);

        call.enqueue(new Callback<ArrayList<Invoice>>() {
            @Override
            public void onResponse(Call<ArrayList<Invoice>> call, Response<ArrayList<Invoice>> response) {
                loading.dismiss();
                if(response.body()!=null) {
                    invoiceList = response.body();
                    showList();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Invoice>> call, Throwable t) {

            }
        });
    }

    private void showList(){
        ArrayList<Invoice> invoice1=new ArrayList<>();
        Collections.reverse(invoiceList);
        if(MainActivity.role.equals("CTR")){
            for (Invoice invoice : invoiceList) {
                if(invoice.getContractor().getCtrEmailId().equals(MainActivity.email)){
                    invoice1.add(invoice);
                }
                invoiceList.clear();
                invoiceList.addAll(invoice1);
            }
        }
        adapter=new InvoiceAdapter(this,invoiceList);
        listView.setAdapter(adapter);
        mAllData.addAll(invoiceList);
        listView.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Invoice invoice=invoiceList.get(position);

        Intent intent=new Intent(this, InvoiceDetailsActivity.class);
        intent.putExtra("INVOICE",invoice);

        startActivity(intent);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        mAllData = new ArrayList<Invoice>();
        for (Invoice invoice : invoiceList) {

            if(invoice.getContractor().getCtrName().toLowerCase().contains(newText.toLowerCase())||invoice.getSequenceId().toLowerCase().contains(newText.toLowerCase())){

                mAllData.add(invoice);
            }

        }
        adapter = new InvoiceAdapter(this, mAllData);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return false;
    }
    public void resetSearch() {
        adapter = new InvoiceAdapter(this, invoiceList);
        listView.setAdapter(adapter);
    }
}
