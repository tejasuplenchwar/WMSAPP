package com.example.admin.wmsapp.Activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.WarehouseAdapter;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.Services.WarehouseAPI;
import com.example.admin.wmsapp.Activity.activity.AddWarehouseActivity;
import com.example.admin.wmsapp.Activity.activity.EditMaterialActivity;
import com.example.admin.wmsapp.Activity.activity.EditWarehouseActivity;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;


/**
 * Created by admin on 02/02/2017.
 */

public class WarehouseFragment extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener  {
    ListView listView;
     ArrayList<Warehouse> warehouses;
    WarehouseAdapter adapter;
    FloatingActionButton fab;
    ArrayList<Warehouse> mAllData=new ArrayList<Warehouse>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse, container, false);
        getWarehouses();

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),AddWarehouseActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Warehouse");
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


    public void getWarehouses(){
        final ProgressDialog loading=ProgressDialog.show(getActivity(),"Fetching Data","Please Wait...",false,false);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WarehouseAPI api=retrofit.create(WarehouseAPI.class);

        Call<ArrayList<Warehouse>> call=api.getWarehouses(MainActivity._orgId);

        call.enqueue(new Callback<ArrayList<Warehouse>>() {
            @Override
            public void onResponse(Call<ArrayList<Warehouse>> call, Response<ArrayList<Warehouse>> response) {
                loading.dismiss();
                if(response.body()!=null){
                    warehouses=response.body();
                    showList();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Warehouse>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });
  }

    private void showList(){

        Collections.reverse(warehouses);
        adapter=new WarehouseAdapter(getActivity(),warehouses);


        listView.setAdapter(adapter);
        mAllData.addAll(warehouses);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
        adapter.notifyDataSetChanged();

    }



    @Override
    public void onResume() {
        super.onResume();
        getWarehouses();
    }

    @Override
    public void onPause() {
        super.onPause();
        getWarehouses();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Edit");
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Warehouse warehouse=warehouses.get(info.position);

        if(item.getTitle().equals("Edit"))
        {
            Intent intent=new Intent(getActivity(), EditWarehouseActivity.class);
            intent.putExtra("WAREHOUSE",warehouse);
            startActivity(intent);
        }
        else if(item.getTitle().equals("Delete"))
        {
            deleteWarehouse(warehouse);
        }
        return super.onContextItemSelected(item);
    }

    public void deleteWarehouse(Warehouse warehouse){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WarehouseAPI api=retrofit.create(WarehouseAPI.class);
        Call<ResponseBody> call=api.deleteWarehouse(warehouse);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(), "Delete Warehouse", Toast.LENGTH_SHORT).show();
                getWarehouses();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        mAllData = new ArrayList<Warehouse>(warehouses);
        for (Warehouse warehouse : warehouses) {
            if (!warehouse.getWarehouseName().toLowerCase().contains(newText.toLowerCase())) {
                mAllData.remove(warehouse);
            }
        }
        adapter = new WarehouseAdapter(getActivity(), mAllData);
        listView.setAdapter(adapter);
        return false;
    }
    public void resetSearch() {
        adapter = new WarehouseAdapter(getActivity(), warehouses);
        listView.setAdapter(adapter);
    }
}
