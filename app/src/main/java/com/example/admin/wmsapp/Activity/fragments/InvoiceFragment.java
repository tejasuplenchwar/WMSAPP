package com.example.admin.wmsapp.Activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.InvoiceAdapter;
import com.example.admin.wmsapp.Activity.RootObject.Invoice;
import com.example.admin.wmsapp.Activity.Services.InvoiceAPI;
import com.example.admin.wmsapp.Activity.activity.InvoiceDetailsActivity;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/**
 * Created by admin on 16/03/2017.
 */

public class InvoiceFragment extends Fragment  implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    ListView listView;
    ArrayList<Invoice> invoiceList;
    ArrayList<Invoice> mAllData=new ArrayList<>();
    InvoiceAdapter adapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        getAllInvoice();
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Invoice");
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView=(ListView)getActivity().findViewById(R.id.listView);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView =(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.menuSearch){

            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllInvoice(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);

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
                loading.dismiss();
                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

   private void showList(){
        ArrayList<Invoice> invoice1=new ArrayList<>();
        Collections.reverse(invoiceList);
        if(MainActivity.role.equals("CTR")){
             try {
                 for (Invoice invoice : invoiceList) {
                     if (invoice.getContractor().getCtrEmailId().equals(MainActivity.email)) {
                         invoice1.add(invoice);
                     }
                }
                 invoiceList.clear();
                 invoiceList.addAll(invoice1);
             }catch (Exception e){
                 Toast.makeText(getActivity(), "Exception"+e, Toast.LENGTH_SHORT).show();
            }
      }
        adapter=new InvoiceAdapter(getActivity(),invoiceList);
        listView.setAdapter(adapter);
        mAllData.addAll(invoiceList);
        listView.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Invoice invoice=invoiceList.get(position);

        Intent intent=new Intent(getActivity(), InvoiceDetailsActivity.class);
        intent.putExtra("INVOICE",invoice);

        startActivity(intent);
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
        mAllData = new ArrayList<Invoice>(invoiceList);
        for (Invoice invoice : invoiceList) {
            if (!invoice.getContractor().getCtrName().toLowerCase().contains(newText.toLowerCase())) {
                mAllData.remove(invoice);
            }
        }
        adapter = new InvoiceAdapter(getActivity(), mAllData);
        listView.setAdapter(adapter);

        return false;
    }
    public void resetSearch() {
        adapter = new InvoiceAdapter(getActivity(), invoiceList);
        listView.setAdapter(adapter);
    }
}
