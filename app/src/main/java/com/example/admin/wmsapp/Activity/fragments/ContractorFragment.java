package com.example.admin.wmsapp.Activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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


import com.example.admin.wmsapp.Activity.Adapter.ContractorAdapter;
import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.Services.ContractorAPI;
import com.example.admin.wmsapp.Activity.activity.AddContractorActivity;
import com.example.admin.wmsapp.Activity.activity.EditContractorActivity;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;

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

public class ContractorFragment extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener  {
      ListView listViewContractors;
    ArrayList<Contractor> contractors;
      ContractorAdapter adapter;
    FloatingActionButton fab;
    ArrayList<Contractor> mAllData=new ArrayList<Contractor>();
    ArrayList<Contractor> filterList=new ArrayList<Contractor>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contractor, container, false);
        fab=(FloatingActionButton)view.findViewById(R.id.fab);

        getContractors();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),AddContractorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       if(getActivity()!=null){
           getActivity().setTitle("Contractor");
       }
        setHasOptionsMenu(true);

  }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewContractors=(ListView)getActivity().findViewById(R.id.listViewContractors);


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


    private void getContractors(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContractorAPI api=retrofit.create(ContractorAPI.class);

        Call<ArrayList<Contractor>> call=api.getContractors(MainActivity._orgId);
        call.enqueue(new Callback<ArrayList<Contractor>>() {
            @Override
            public void onResponse(Call<ArrayList<Contractor>> call, Response<ArrayList<Contractor>> response) {
               loading.dismiss();
                if (response.body() != null) {
                    contractors=response.body();
                    showList();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Contractor>> call, Throwable t) {

            }
        });


    }
    private void showList(){
        //String array to store all the book names
        Collections.reverse(contractors);
        adapter=new ContractorAdapter(getActivity(),contractors);


        listViewContractors.setAdapter(adapter);
        mAllData.addAll(contractors);
        listViewContractors.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
        registerForContextMenu(listViewContractors);
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
        Contractor contractor=contractors.get(info.position);

        if(item.getTitle().equals("Edit"))
        {
            Intent intent=new Intent(getActivity(), EditContractorActivity.class);
            intent.putExtra("CONTRACTOR",contractor);
            startActivity(intent);
        }
        else if(item.getTitle().equals("Delete"))
        {
            deleteContractor(contractor);
        }
        return super.onContextItemSelected(item);
    }


    public void deleteContractor(Contractor contractor){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContractorAPI api=retrofit.create(ContractorAPI.class);

        Call<ResponseBody> call=api.deleteContractor(contractor);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(), "Delete Contractor Successfully....!!!", Toast.LENGTH_SHORT).show();
                getContractors();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public void onResume() {
        super.onResume();
        getContractors();
    }
    @Override
    public void onPause() {
        super.onPause();
        getContractors();
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
        mAllData = new ArrayList<Contractor>(contractors);
        for (Contractor contractor : contractors) {
            if (!contractor.getCtrName().toLowerCase().contains(newText.toLowerCase())) {
                mAllData.remove(contractor);
            }
           /* if(contractor.getCtrName().toLowerCase().contains(newText.toLowerCase())||contractor.getCtrAddress().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(contractor);
            }*/
        }
        if(getActivity()!=null) {
            adapter = new ContractorAdapter(getActivity(), mAllData);
            listViewContractors.setAdapter(adapter);
        }
        return false;
    }
    public void resetSearch() {
        adapter = new ContractorAdapter(getActivity(), contractors);
        listViewContractors.setAdapter(adapter);
    }
}
