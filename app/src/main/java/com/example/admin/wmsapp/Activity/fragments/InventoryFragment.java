package com.example.admin.wmsapp.Activity.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.InventoryAdapter;
import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.Services.InventoryAPI;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;


/**
 * Created by admin on 02/02/2017.
 */

public class InventoryFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    ListView listView;
    ArrayList<Inventory> inventories;
    InventoryAdapter adapter;
    ArrayList<Inventory> mAllData=new ArrayList<Inventory>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_inventory, container, false);
        getInventory();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Inventory");
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

    private void getInventory(){
        final ProgressDialog loading=ProgressDialog.show(getActivity(),"Fetching Data","Please Wait...",false,false);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InventoryAPI api=retrofit.create(InventoryAPI.class);

        Call<ArrayList<Inventory>> call=api.getInventory(MainActivity._orgId);

        call.enqueue(new Callback<ArrayList<Inventory>>() {
            @Override
            public void onResponse(Call<ArrayList<Inventory>> call, Response<ArrayList<Inventory>> response) {
                loading.dismiss();
                if(response.body()!=null){
                    inventories=response.body();
                    showList();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Inventory>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showList(){



       Collections.reverse(inventories);
       adapter=new InventoryAdapter(getActivity(),inventories);


        listView.setAdapter(adapter);
        mAllData.addAll(inventories);

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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        mAllData = new ArrayList<Inventory>();
        for (Inventory inventory : inventories) {
            if (inventory.getWarehouse().getWarehouseName().toLowerCase().contains(newText.toLowerCase())
                    ||inventory.getMaterial().getMaterialName().toLowerCase().contains(newText.toLowerCase())
                    ||inventory.getMaterial().getMaterialCode().toLowerCase().contains(newText.toLowerCase())) {
                mAllData.add(inventory);
            }
        }
        adapter = new InventoryAdapter(getActivity(), mAllData);
        listView.setAdapter(adapter);
        return false;
    }
    public void resetSearch() {
        adapter = new InventoryAdapter(getActivity(), inventories);
        listView.setAdapter(adapter);
    }
}
