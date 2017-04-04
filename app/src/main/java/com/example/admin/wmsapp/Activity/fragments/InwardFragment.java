package com.example.admin.wmsapp.Activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.InwardAdapter;
import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.Activity.Model.Inward;
import com.example.admin.wmsapp.Activity.Services.InwardAPI;
import com.example.admin.wmsapp.Activity.activity.Abcd;
import com.example.admin.wmsapp.Activity.activity.AddInwardActivity;
import com.example.admin.wmsapp.Activity.activity.InwardDetailsActivity;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;


public class InwardFragment extends Fragment implements  AdapterView.OnItemClickListener,SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    ListView listView;
    ArrayList<Inward> inwards;
    InwardAdapter adapter;
    FloatingActionButton fab;
    ArrayList<Inward> mAllData=new ArrayList<Inward>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inward, container, false);
        getInwards();
        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddInwardActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Inward");
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
        searchView.setQueryHint("Search By Warehouse");
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



    @Override
    public void onResume() {
        super.onResume();
        getInwards();
    }




    private void getInwards(){
       final ProgressDialog loading=ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InwardAPI api=retrofit.create(InwardAPI.class);

        Call<ArrayList<Inward>> call=api.getInwards(MainActivity._orgId);

       call.enqueue(new Callback<ArrayList<Inward>>() {
           @Override
           public void onResponse(Call<ArrayList<Inward>> call, Response<ArrayList<Inward>> response) {
               loading.dismiss();
              if(response.body()!=null) {
                  inwards = response.body();
                  showList();
              }
           }

           @Override
           public void onFailure(Call<ArrayList<Inward>> call, Throwable t) {

           }
       });
    }

    private void showList()
    {

        listView.setOnItemClickListener(this);
        Collections.reverse(inwards);
        adapter=new InwardAdapter(getActivity(),inwards);
        listView.setAdapter(adapter);
        mAllData.addAll(inwards);

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Inward inward=inwards.get(position);
        Intent intent=new Intent(getActivity(), InwardDetailsActivity.class);
       intent.putExtra("INWARD",inward);
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        mAllData = new ArrayList<Inward>(inwards);
        for (Inward inward : inwards) {
            if (!inward.getWarehouse().getWarehouseName().toLowerCase().contains(newText.toLowerCase())) {
                mAllData.remove(inward);
            }
        }
        adapter = new InwardAdapter(getActivity(), mAllData);
        listView.setAdapter(adapter);
        return false;
    }
    public void resetSearch() {
        adapter = new InwardAdapter(getActivity(), inwards);
        listView.setAdapter(adapter);
    }
}
