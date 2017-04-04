package com.example.admin.wmsapp.Activity.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Services.ContractorAPI;
import com.example.admin.wmsapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class EditContractorActivity extends AppCompatActivity {

    EditText editContractorName,editContractorAddress,editContractorNumber,editContractorEmail;
    TextView textContractorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contractor);

        setTitle("Edit Contractor");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Contractor contractor= (Contractor) getIntent().getSerializableExtra("CONTRACTOR");

        textContractorId= (TextView) findViewById(R.id.textContractorId);
        editContractorName= (EditText) findViewById(R.id.editContractorName);
        editContractorAddress= (EditText) findViewById(R.id.editContractorAddress);
        editContractorNumber= (EditText) findViewById(R.id.editContractorNumber);
        editContractorEmail=(EditText) findViewById(R.id.editContractorEmail);

        textContractorId.setText(""+contractor.getCtrId());
        editContractorName.setText(contractor.getCtrName());
        editContractorAddress.setText(contractor.getCtrAddress());
        editContractorNumber.setText(contractor.getCtrNum());
        editContractorEmail.setText(contractor.getCtrEmailId());


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

        Contractor contractor=new Contractor();

        contractor.setCtrId(Integer.parseInt(textContractorId.getText().toString()));
        contractor.setCtrName(editContractorName.getText().toString());
        contractor.setCtrAddress(editContractorAddress.getText().toString());
        contractor.setCtrNum(editContractorNumber.getText().toString());
        contractor.setCtrEmailId(editContractorEmail.getText().toString());

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContractorAPI api=retrofit.create(ContractorAPI.class);

        Call<ResponseBody> call=api.editContractor(contractor);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(EditContractorActivity.this, "Edited Contractor Successfully...!!!!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
