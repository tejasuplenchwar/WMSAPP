package com.example.admin.wmsapp.Activity.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.Services.WarehouseAPI;
import com.example.admin.wmsapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class AddWarehouseActivity extends AppCompatActivity {
  EditText editWarehouseName,editWarehouseRegion,editWarehouseLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);
        setTitle("Add Warehouse");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        editWarehouseName=(EditText)findViewById(R.id.editWarehouseName);
        editWarehouseRegion=(EditText)findViewById(R.id.editWarehouseRegion);
        editWarehouseLoc=(EditText)findViewById(R.id.editWarehouseloc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addWarehouse(View v) {
        if(editWarehouseName.getText().length()==0)
        {
            editWarehouseName.setError("enter warehouse name");
        }
       if(editWarehouseLoc.getText().length()==0)
        {
            editWarehouseLoc.setError("enter warehouse location");
        }
       if(editWarehouseRegion.getText().length()==0){
            editWarehouseRegion.setError("enter warehouse region");
        }
        else{
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseName(editWarehouseName.getText().toString());
            warehouse.setwRegion(editWarehouseRegion.getText().toString());
            warehouse.setWarehouseLoc(editWarehouseLoc.getText().toString());
            warehouse.setOrganisationId(MainActivity._orgId);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WarehouseAPI api = retrofit.create(WarehouseAPI.class);

            Call<ResponseBody> call = api.createWarehouse(warehouse);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AddWarehouseActivity.this, "Record Added Successfully...!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    }
}
