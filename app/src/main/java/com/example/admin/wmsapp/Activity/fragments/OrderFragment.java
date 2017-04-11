package com.example.admin.wmsapp.Activity.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.OrderAdapter;
import com.example.admin.wmsapp.Activity.Model.Order;
import com.example.admin.wmsapp.Activity.Services.OrderAPI;
import com.example.admin.wmsapp.Activity.activity.AddInwardActivity;
import com.example.admin.wmsapp.Activity.activity.AddOrderActivity;
import com.example.admin.wmsapp.Activity.activity.GenerateInvoice;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.Activity.activity.OrderDetailsActivity;
import com.example.admin.wmsapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;


/**
 * Created by admin on 02/02/2017.
 */

public class OrderFragment extends Fragment implements AdapterView.OnItemClickListener,SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{
     ListView listView;
     OrderAdapter adapter;
     ArrayList<Order> orders=new ArrayList<>();
    ArrayList<Order> mAllData=new ArrayList<Order>();
    List<Object[]> invoiceData=new ArrayList<>();
    List<Object[]> objects=new ArrayList<>();
    String result = "";
    FloatingActionButton fab;
    Button button;

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    OrderAPI api=retrofit.create(OrderAPI.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.fragment_order, container, false);
        getOrders();
        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), AddOrderActivity.class);
                startActivity(intent);
            }
        });
        listView= (ListView) view.findViewById(R.id.listView);
        button=(Button)view.findViewById(R.id.buttonClick);

        if(MainActivity.role.equals("CTR")){
            button.setVisibility(View.GONE);
        }

        if(MainActivity.role.equals("WHM")){
            fab.setVisibility(View.GONE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int i=0;
                String comma="";
                for (Order order : adapter.getBox()) {
                     if(i==0){
                         comma =""; }
                    else {
                         comma = ",";
                     }
                    if (order.isBox()){
                        result += comma + order.getOrderId();
                    }
                    i++;
              /* List<Integer> list=new ArrayList<Integer>();
                for (Order order : adapter.getBox()){
                    list.add(order.getOrderId());*/
            }

               //create invoice
                if(result.equals("")){
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Please Select orders to display....");
                     alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else{
                    Intent intent=new Intent(getActivity(), GenerateInvoice.class);
                    intent.putExtra("RESULT",result);
                    result="";
                    startActivity(intent);
                }




            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Order");
        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView =(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search By Contractor");
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

  private void getOrders(){
        final ProgressDialog loading=ProgressDialog.show(getActivity(),"Fetching Data","Please Wait...",false,false);



        Call<ArrayList<Order>> call=api.getOrders(MainActivity._orgId);

        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                loading.dismiss();
                if(response.body()!=null) {
                    orders = response.body();
                    showList();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showList(){
        ArrayList<Order> orders1=new ArrayList<>();
        listView.setOnItemClickListener(this);
        Collections.reverse(orders);
        if(MainActivity.role.equals("CTR")){
            for(Order order:orders){
                if(order.getContractor().getCtrEmailId().equals(MainActivity.email)){
                  orders1.add(order);
                }
            }
            orders.clear();
            orders.addAll(orders);
        }
        adapter=new OrderAdapter(getActivity(),orders);
        mAllData.addAll(orders);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Order order=orders.get(position);

        Intent intent=new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra("ORDER",order);
        startActivity(intent);


    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders();

    }


    @Override
    public void onPause() {
        super.onPause();
        getOrders();
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
        mAllData = new ArrayList<Order>();
        for (Order order : orders) {
            if (order.getContractor().getCtrName().toLowerCase().contains(newText.toLowerCase())
                    ||order.getSequenceId().toLowerCase().contains(newText.toLowerCase())
                    ||order.getWarehouse().getWarehouseName().toLowerCase().contains(newText.toLowerCase())
                    ||order.getOrderMasterInvoiceStatus().toLowerCase().contains(newText.toLowerCase())) {
                mAllData.add(order);
            }
        }
        adapter = new OrderAdapter(getActivity(), mAllData);
        listView.setAdapter(adapter);
        return false;
    }
    public void resetSearch() {
        adapter = new OrderAdapter(getActivity(), orders);
        listView.setAdapter(adapter);
    }
}
