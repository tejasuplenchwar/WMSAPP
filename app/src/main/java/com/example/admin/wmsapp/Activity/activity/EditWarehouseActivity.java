package com.example.admin.wmsapp.Activity.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditWarehouseActivity extends AppCompatActivity {

    EditText editWarehouseName, editWarehouseloc, editWarehouseRegion;
    TextView  textWarehouseId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_warehouse);

        setTitle("Edit Warehouse");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        Warehouse warehouse= (Warehouse) getIntent().getSerializableExtra("WAREHOUSE");


        textWarehouseId=(TextView)findViewById(R.id.textWarehouseId);
        editWarehouseName=(EditText)findViewById(R.id.editWarehouseName);
        editWarehouseloc=(EditText)findViewById(R.id.editWarehouseloc);
        editWarehouseRegion=(EditText)findViewById(R.id.editWarehouseRegion);



        textWarehouseId.setText(""+warehouse.getWarehouseId());
        editWarehouseName.setText(warehouse.getWarehouseName());
        editWarehouseloc.setText(warehouse.getWarehouseLoc());
        editWarehouseRegion.setText(warehouse.getwRegion());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void update(View v){

        Warehouse warehouse=new Warehouse();
        warehouse.setWarehouseId(Integer.parseInt(textWarehouseId.getText().toString()));
        warehouse.setWarehouseName(editWarehouseName.getText().toString());
        warehouse.setWarehouseLoc(editWarehouseloc.getText().toString());
        warehouse.setwRegion(editWarehouseRegion.getText().toString());


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WarehouseAPI api=retrofit.create(WarehouseAPI.class);

        Call<ResponseBody> call= api.editWarehouse(warehouse);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(EditWarehouseActivity.this, "Edited warehouse Successfully...!!!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
