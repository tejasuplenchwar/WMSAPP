package com.example.admin.wmsapp.Activity.fragments;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.CategoryAdapter;
import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.Activity.Services.CategoryAPI;
import com.example.admin.wmsapp.Activity.activity.AddCategoryActivity;
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
 * Created by admin on 31/03/2017.
 */

public class CategoryFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    AlertDialog dialog;
    ListView listView;
    ArrayList<Category> categoryArrayList=new ArrayList<Category>();
    ArrayList<Category> mAllData=new ArrayList<>();
    CategoryAdapter adapter;
    FloatingActionButton fab;

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    CategoryAPI api=retrofit.create(CategoryAPI.class);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        getAllCategory();
        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
                View v1=getActivity().getLayoutInflater().inflate(R.layout.activity_add_category,null);
                final EditText editCategoryName= (EditText) v1.findViewById(R.id.editCategoryName);
                final EditText editCategoryDesc= (EditText) v1.findViewById(R.id.editCategoryDescription);
                Button categoryAdd= (Button) v1.findViewById(R.id.categoryAdd);
                categoryAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Category category=new Category();
                        category.setCategoryName(editCategoryName.getText().toString());
                        category.setCategoryDesc(editCategoryDesc.getText().toString());
                        category.setOrganisationId(MainActivity._orgId);
                        Call<ResponseBody> call=api.createCategory(category);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(getActivity(), "added category", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                getAllCategory();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                        Toast.makeText(getActivity(), "buttonclicked", Toast.LENGTH_SHORT).show();

                    }
                });
                mBuilder.setView(v1);
                dialog=mBuilder.create();
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Category");
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView=(ListView)getActivity().findViewById(R.id.listView);

    }



    private void getAllCategory(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);



        Call<ArrayList<Category>> call=api.getAllCategory(MainActivity._orgId);

        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                loading.dismiss();
                if(response.body()!=null) {
                    categoryArrayList = response.body();
                    showList();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

            }
        });

    }

    public void showList(){
        Collections.reverse(categoryArrayList);
        adapter=new CategoryAdapter(getActivity(),categoryArrayList);
        listView.setAdapter(adapter);
        mAllData.addAll(categoryArrayList);
        adapter.notifyDataSetChanged();
        registerForContextMenu(listView);
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
       Category category=categoryArrayList.get(info.position);
        if(item.getTitle().equals("Edit"))
        {
            editCategory(category);
            /*Intent intent=new Intent(getActivity(), EditContractorActivity.class);
            intent.putExtra("CONTRACTOR",contractor);
            startActivity(intent);*/
        }
        else if(item.getTitle().equals("Delete"))
        {
           // deleteContractor(contractor);
        }
        return super.onContextItemSelected(item);
    }


    private void editCategory(final Category category){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
        View v1=getActivity().getLayoutInflater().inflate(R.layout.activity_add_category,null);
        final EditText editCategoryName= (EditText) v1.findViewById(R.id.editCategoryName);
        final EditText editCategoryDesc= (EditText) v1.findViewById(R.id.editCategoryDescription);
        editCategoryName.setText(category.getCategoryName());
        editCategoryDesc.setText(category.getCategoryDesc());
        Button categoryAdd= (Button) v1.findViewById(R.id.categoryAdd);
        categoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category categoryEdit=new Category();
                categoryEdit.setCategoryId(category.getCategoryId());
                categoryEdit.setCategoryName(editCategoryName.getText().toString());
                categoryEdit.setCategoryDesc(editCategoryDesc.getText().toString());
                categoryEdit.setOrganisationId(MainActivity._orgId);
                Call<ResponseBody> call=api.editCategory(categoryEdit);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getActivity(), "Category update successfully....!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getAllCategory();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        categoryAdd.setText("Update");
        mBuilder.setView(v1);
        dialog=mBuilder.create();
        dialog.show();
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
        mAllData = new ArrayList<Category>();
        for (Category category : categoryArrayList) {
            if (category.getCategoryName().toLowerCase().contains(newText.toLowerCase())
                    ||category.getCategoryDesc().toLowerCase().contains(newText.toLowerCase())
                   ) {
                mAllData.add(category);
            }

        }

        adapter = new CategoryAdapter(getActivity(), mAllData);
        listView.setAdapter(adapter);

        return false;

    }

    public void resetSearch() {
        adapter = new CategoryAdapter(getActivity(), categoryArrayList);
        listView.setAdapter(adapter);
    }
}
