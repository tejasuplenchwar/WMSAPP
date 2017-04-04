package com.example.admin.wmsapp.Activity.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.wmsapp.Activity.Adapter.MenuAdapter;
import com.example.admin.wmsapp.Activity.Services.MenuAPI;
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

public class Menu extends Fragment {
    ListView listView;
    ArrayList<com.example.admin.wmsapp.Activity.Model.Menu> menuList=new ArrayList<>();
    MenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.menu, container, false);
        listView= (ListView) view.findViewById(R.id.listView);
        getAllMenu();
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

    private void getAllMenu(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                 .build();

        MenuAPI api=retrofit.create(MenuAPI.class);
        Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Menu>> call=api.getAllMenu();
        call.enqueue(new Callback<ArrayList<com.example.admin.wmsapp.Activity.Model.Menu>>() {
            @Override
            public void onResponse(Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Menu>> call, Response<ArrayList<com.example.admin.wmsapp.Activity.Model.Menu>> response) {


                if(response.body()!=null){
                    menuList=response.body();
                     showList();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Menu>> call, Throwable t) {

            }
        });

    }

    private void showList(){
        Collections.reverse(menuList);
        adapter=new MenuAdapter(getActivity(),menuList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
