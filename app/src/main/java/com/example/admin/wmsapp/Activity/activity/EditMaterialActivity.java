package com.example.admin.wmsapp.Activity.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Services.MaterialAPI;
import com.example.admin.wmsapp.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class EditMaterialActivity extends AppCompatActivity {

    EditText editMaterialName,editMaterialDesc,editUnitOfMeasure,editUnitPrice,editMaterialCode;
    TextView textMaterialId,textMaterialCategory;
    SearchableSpinner spinnerSearch;

    ArrayList<Category> categories=new ArrayList<>();

    Material material1=new Material();
    Material material=new Material();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_material);
        setTitle("Edit Material");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        material= (Material) getIntent().getSerializableExtra("MATERIAL");

        editMaterialName=(EditText)findViewById(R.id.editMaterialName);
        editMaterialDesc=(EditText)findViewById(R.id.editMaterialDesc);
        editUnitOfMeasure=(EditText)findViewById(R.id.editUnitOfMeasure);
        editUnitPrice=(EditText)findViewById(R.id.editUnitPrice);
        editMaterialCode=(EditText)findViewById(R.id.editMaterialCode);
        spinnerSearch= (SearchableSpinner) findViewById(R.id.spinnerSearch);
        textMaterialId=(TextView)findViewById(R.id.textMaterialID);
        textMaterialCategory= (TextView) findViewById(R.id.textMaterialCategory);

        textMaterialId.setText(""+material.getMaterialID());
        editMaterialName.setText(material.getMaterialName());
        editMaterialDesc.setText(material.getMaterialDesc());
        editUnitOfMeasure.setText(material.getUnitOfMeasure());
        editUnitPrice.setText(String.valueOf(material.getUnitPrice()));
        editMaterialCode.setText(material.getMaterialCode());
        textMaterialCategory.setText(material.getCategory().getCategoryName());

        getAllCategories();
    }
    private void getAllCategories(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MaterialAPI api=retrofit.create(MaterialAPI.class);
        Call<ArrayList<Category>> call=api.getAllCategory(MainActivity._orgId);
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                categories= response.body();
                showCategory();
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

            }
        });

    }

    private void showCategory(){
        ArrayAdapter<Category> categoryAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        spinnerSearch.setAdapter(categoryAdapter);
        spinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category= (Category) parent.getSelectedItem();
                material.setCategory(category);
                textMaterialCategory.setText(material.getCategory().getCategoryName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        material1.setMaterialID(Integer.parseInt(textMaterialId.getText().toString()));
        material1.setMaterialName(editMaterialName.getText().toString());
        material1.setMaterialDesc(editMaterialDesc.getText().toString());
        material1.setUnitOfMeasure(editUnitOfMeasure.getText().toString());
        material1.setUnitPrice(Double.parseDouble(editUnitPrice.getText().toString()));
        material1.setMaterialCode(editMaterialCode.getText().toString());
        material1.setOrganisationId(MainActivity._orgId);
        material1.setCategory(material.getCategory());
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MaterialAPI api=retrofit.create(MaterialAPI.class);

        Call<ResponseBody> call=api.editMaterial(material);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(EditMaterialActivity.this, "Edited Material Successfully..!!!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

}
