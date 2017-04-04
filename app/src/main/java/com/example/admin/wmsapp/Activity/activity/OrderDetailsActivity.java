package com.example.admin.wmsapp.Activity.activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.admin.wmsapp.Activity.Adapter.OrderDetailsAdapter;
import com.example.admin.wmsapp.Activity.Model.Order;
import com.example.admin.wmsapp.Activity.Model.OrderDetails;
import com.example.admin.wmsapp.Activity.Services.OrderAPI;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class OrderDetailsActivity extends AppCompatActivity {
  ListView listView;
  ArrayList<OrderDetails> orderDetails;
    OrderDetailsAdapter adapter;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        setTitle("Order Details");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        listView=(ListView)findViewById(R.id.listView);
        Order order=(Order)getIntent().getSerializableExtra("ORDER");
        orderId=order.getOrderId();
        getOrderDetails();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getOrderDetails(){
        final ProgressDialog loading= ProgressDialog.show(this,"Fetching Data","Please",false,false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderAPI api=retrofit.create(OrderAPI.class);

        Call<ArrayList<OrderDetails>> call=api.getOrderDetails();

        call.enqueue(new Callback<ArrayList<OrderDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderDetails>> call, Response<ArrayList<OrderDetails>> response) {
                loading.dismiss();
                orderDetails=response.body();
                showList();
            }

            @Override
            public void onFailure(Call<ArrayList<OrderDetails>> call, Throwable t) {

            }
        });
    }

    private void showList(){
        ArrayList<OrderDetails> details=new ArrayList<>();
        for(OrderDetails orderDetail : orderDetails) {

            if(orderDetail.getOrder().getOrderId()==orderId ){
                details.add(orderDetail);
            }
        }
        adapter=new OrderDetailsAdapter(this,details);
        listView.setAdapter(adapter);


    }
}
