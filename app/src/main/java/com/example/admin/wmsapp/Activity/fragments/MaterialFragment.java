package com.example.admin.wmsapp.Activity.fragments;


import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;


import android.view.ContextMenu;
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

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

import com.example.admin.wmsapp.Activity.Adapter.MaterialAdapter;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Services.MaterialAPI;
import com.example.admin.wmsapp.Activity.activity.AddMaterialActivity;
import com.example.admin.wmsapp.Activity.activity.EditMaterialActivity;
import com.example.admin.wmsapp.Activity.activity.MaterialDetailsActivity;
import com.example.admin.wmsapp.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 02/02/2017.
 */

public class MaterialFragment extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener  {
    ListView listViewMaterials;
    ArrayList<Material> materials;
    ArrayList<Material> mAllData = new ArrayList<Material>();
    MaterialAdapter adapter;
    View view;
    FloatingActionButton fab;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_material, container, false);
        getMaterials();

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),AddMaterialActivity.class);
                startActivity(intent);

            }
        });

        return view;
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
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        mAllData = new ArrayList<Material>();
        for (Material material : materials) {
            if (material.getMaterialName().toLowerCase().contains(newText.toLowerCase())||material.getMaterialCode().toLowerCase().contains(newText.toLowerCase())) {
               mAllData.add(material);
              }
            }



        adapter = new MaterialAdapter(getActivity(), mAllData);


        listViewMaterials.setAdapter(adapter);
        return false;
    }

    public void resetSearch() {
        adapter = new MaterialAdapter(getActivity(), materials);
        listViewMaterials.setAdapter(adapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.menuSearch){

            return  true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Material");

        setHasOptionsMenu(true);


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewMaterials=(ListView)getActivity().findViewById(R.id.listViewMaterials);
        listViewMaterials.setOnItemClickListener(this);


    }
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    private void getMaterials(){
        final ProgressDialog loading=ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        MaterialAPI api =retrofit.create(MaterialAPI.class);

        Call<ArrayList<Material>> call=api.getMaterials();

        call.enqueue(new Callback<ArrayList<Material>>() {
            @Override
            public void onResponse(Call<ArrayList<Material>> call, Response<ArrayList<Material>> response) {
                loading.dismiss();
                if(response.body()!=null){
                    materials=response.body();
                    showlist();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Material>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Error while fetching data..."+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showlist(){

        listViewMaterials.setOnItemClickListener(this);
        mAllData.addAll(materials);
        Collections.reverse(materials);
        adapter=new MaterialAdapter(getActivity(),materials);
        /*listViewMaterials.post(new Runnable() {
            @Override
            public void run() {
                listViewMaterials.smoothScrollToPosition(0);
            }
        });*/
        listViewMaterials.setAdapter(adapter);
        registerForContextMenu(listViewMaterials);
        //adapter.notifyItemInserted(0);
        adapter.notifyDataSetChanged();


    }



    @Override
    public void onResume() {
        super.onResume();
        getMaterials();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMaterials();
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
        Material material=materials.get(info.position);

        if(item.getTitle().equals("Edit"))
        {
            Intent intent=new Intent(getActivity(), EditMaterialActivity.class);
            intent.putExtra("MATERIAL",material);
            startActivity(intent);
        }
        else if(item.getTitle().equals("Delete"))
        {
            deleteMaterial(material);
        }
        return super.onContextItemSelected(item);
    }

    public void deleteMaterial(Material material){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MaterialAPI api =retrofit.create(MaterialAPI.class);

        Call<ResponseBody> call=api.deleteMaterial(material);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(), "Delete Material", Toast.LENGTH_SHORT).show();
                getMaterials();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Material material= materials.get(position);
        Intent intent=new Intent(getActivity(), MaterialDetailsActivity.class);
        intent.putExtra("Material",material);
        startActivity(intent);
    }
}
