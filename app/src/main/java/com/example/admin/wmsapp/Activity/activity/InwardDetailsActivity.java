package com.example.admin.wmsapp.Activity.activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.admin.wmsapp.Activity.Adapter.InwardDetailsAdapter;
import com.example.admin.wmsapp.Activity.Model.Inward;
import com.example.admin.wmsapp.Activity.Model.InwardDetails;
import com.example.admin.wmsapp.Activity.Services.InwardAPI;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class InwardDetailsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<InwardDetails> inwardDetails;

    InwardDetailsAdapter adapter;
    private int inwardId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_details);

        setTitle("Inward Details");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        Inward inward= (Inward) getIntent().getSerializableExtra("INWARD");
        inwardId=inward.getInId();
        getInwardDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getInwardDetails(){

        final ProgressDialog loading=ProgressDialog.show(this,"Fetching Data","Please",false,false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InwardAPI api=retrofit.create(InwardAPI.class);

        Call<ArrayList<InwardDetails>> call=api.getInwardDetails();
        call.enqueue(new Callback<ArrayList<InwardDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<InwardDetails>> call, Response<ArrayList<InwardDetails>> response) {
                loading.dismiss();
                if(response.body()!=null){
                    inwardDetails=response.body();
                    showList();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<InwardDetails>> call, Throwable t) {

            }
        });

    }

    public void showList() {

        ArrayList<InwardDetails> inwardDetailses=new ArrayList<InwardDetails>();
        for(InwardDetails inwardDetail : inwardDetails) {

            if(inwardDetail.getInward().getInId()==inwardId ){
                inwardDetailses.add(inwardDetail);
            }
        }
        listView= (ListView) findViewById(R.id.listView);
        adapter=new InwardDetailsAdapter(this,inwardDetailses);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
