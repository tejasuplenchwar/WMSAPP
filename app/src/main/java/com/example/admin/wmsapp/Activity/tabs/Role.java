package com.example.admin.wmsapp.Activity.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.wmsapp.Activity.Adapter.RoleAdapter;
import com.example.admin.wmsapp.Activity.Model.*;
import com.example.admin.wmsapp.Activity.Services.RoleAPI;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/**
 * Created by admin on 04/04/2017.
 */

public class Role extends Fragment{
    ListView listView;
    ArrayList<com.example.admin.wmsapp.Activity.Model.Role> roleList=new ArrayList<>();
    RoleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.role, container, false);
        listView= (ListView) view.findViewById(R.id.listView);
        getAllRole();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getAllRole(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RoleAPI api=retrofit.create(RoleAPI.class);

        Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>> call=api.getAllRole();

        call.enqueue(new Callback<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>>() {
            @Override
            public void onResponse(Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>> call, Response<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>> response) {
                roleList=response.body();
                showList();
            }

            @Override
            public void onFailure(Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>> call, Throwable t) {

            }
        });
    }

    private void showList(){
        Collections.reverse(roleList);
        adapter=new RoleAdapter(getActivity(),roleList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
