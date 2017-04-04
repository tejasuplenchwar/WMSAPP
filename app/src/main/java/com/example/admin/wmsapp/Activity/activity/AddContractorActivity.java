package com.example.admin.wmsapp.Activity.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.Activity.Services.ContractorAPI;
import com.example.admin.wmsapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/*import com.example.admin.wmsapp.R;*/

public class AddContractorActivity extends AppCompatActivity {
 EditText editContractorName,editContractorAddress,editContractorNumber,editContractorEmail;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contractor);
        setTitle("Add Contractor");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        editContractorName=(EditText)findViewById(R.id.editContractorName);
        editContractorAddress=(EditText)findViewById(R.id.editContractorAddress);
        editContractorNumber=(EditText)findViewById(R.id.editContractorNumber);
        editContractorEmail= (EditText) findViewById(R.id.editContractorEmail);
 }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




    public void addContractor(View v) {
        if(editContractorName.getText().length()==0)
        {
            editContractorName.setError("enter contractor name");
        }
        else if(editContractorAddress.getText().length()==0)
        {
            editContractorAddress.setError("enter contractor address");
        }
        else if(editContractorNumber.getText().length()==0){
            editContractorNumber.setError("enter contractor number");
        }
        else if(editContractorEmail.getText().length()==0){
            editContractorEmail.setError("enter contractor number");
        }
        else{
            Contractor contractor = new Contractor();
            contractor.setCtrName(editContractorName.getText().toString());
            contractor.setCtrAddress(editContractorAddress.getText().toString());
            contractor.setCtrNum(editContractorNumber.getText().toString());
            contractor.setCtrEmailId(editContractorEmail.getText().toString());
            contractor.setOrganisationId(MainActivity._orgId);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ContractorAPI api = retrofit.create(ContractorAPI.class);

            Call<ResponseBody> call = api.createContractor(contractor);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AddContractorActivity.this, "added contractor successfully...!!!", Toast.LENGTH_SHORT).show();
                      finish();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            //
            //finish();
        }
    }

}
