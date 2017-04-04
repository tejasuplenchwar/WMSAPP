package com.example.admin.wmsapp.Activity.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.AddInwardAdapter;
import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.RootObject.Inward;
import com.example.admin.wmsapp.Activity.RootObject.InwardDetails;
import com.example.admin.wmsapp.Activity.RootObject.MaterialList;
import com.example.admin.wmsapp.Activity.RootObject.RootObject;
import com.example.admin.wmsapp.Activity.Services.InwardAPI;
import com.example.admin.wmsapp.Activity.Services.MaterialAPI;
import com.example.admin.wmsapp.Activity.Services.WarehouseAPI;
import com.example.admin.wmsapp.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class AddInwardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    List<MaterialList> materials=new ArrayList<>();

    EditText editMatQuantity;
    TextView textUnitOfMeasure;

    ListView listView;

    AddInwardAdapter adapter;
    ArrayList<Category> categories=new ArrayList<>();

    SearchableSpinner spinnerCategory,spinner,wSpinner;
    String materialName,unitOfMeasure;
    int materialId,warehouseId;
     int count;
     int categoryId;
    ArrayList<Material> smaterials=new ArrayList<>();
    ArrayList<Warehouse> sWarehouses=new ArrayList<>();



    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inward);
        setTitle("Add Inwards");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        textUnitOfMeasure=(TextView)findViewById(R.id.textUnitOfMeasure) ;
        editMatQuantity= (EditText) findViewById(R.id.editMatQuantity);
        listView = (ListView) findViewById(R.id.listView);

        spinnerCategory= (SearchableSpinner) findViewById(R.id.spinnerCategory);


        spinnerWarehouse();
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
               if(response.body()!=null){
                   categories= response.body();
                   showCategory();
               }

            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

            }
        });

    }

    private void showCategory(){
        ArrayAdapter<Category> categoryAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category= (Category) parent.getSelectedItem();
                categoryId=category.getCategoryId();
                spinnerMaterial();
           }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void spinnerWarehouse(){
        WarehouseAPI api=retrofit.create(WarehouseAPI.class);
        Call<ArrayList<Warehouse>> call=api.getWarehouses();
        call.enqueue(new Callback<ArrayList<Warehouse>>() {
            @Override
            public void onResponse(Call<ArrayList<Warehouse>> call, Response<ArrayList<Warehouse>> response) {
                if(response.body()!=null){
                    sWarehouses=response.body();
                    showWarehouse();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Warehouse>> call, Throwable t) {

            }
        });
    }

    private void showWarehouse(){
        ArrayList<Warehouse> spinnerWarehouse=new ArrayList<>();
        wSpinner= (SearchableSpinner) findViewById(R.id.wSpinner);
        for(Warehouse warehouse : sWarehouses) {
            spinnerWarehouse.add(new Warehouse(warehouse.getWarehouseId(),warehouse.getWarehouseName()));
        }
        ArrayAdapter<Warehouse> dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerWarehouse);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        wSpinner.setAdapter(dataAdapter);
        wSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Warehouse warehouse = (Warehouse) parent.getSelectedItem();
                warehouseId=warehouse.getWarehouseId();
                count++;
                if(count==2)
                {
                    wSpinner.setEnabled(false);
                }

                Toast.makeText(AddInwardActivity.this, "warehouseId:-"+warehouseId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void spinnerMaterial(){
        MaterialAPI api=retrofit.create(MaterialAPI.class);
        Call<ArrayList<Material>> call=api.getMaterials();
        call.enqueue(new Callback<ArrayList<Material>>() {
            @Override
            public void onResponse(Call<ArrayList<Material>> call, Response<ArrayList<Material>> response) {
               if(response.body()!=null){
                   smaterials=response.body();
                   System.out.println(response.body());
                   System.out.println(smaterials);
                   spinnerItems();
               }

            }

            @Override
            public void onFailure(Call<ArrayList<Material>> call, Throwable t) {

            }
        });
         Log.d("Add Inward Activity","all Materials:-"+smaterials);
         System.out.println(smaterials);

    }

    private void spinnerItems(){
       ArrayList<Material> spinnerArray=new ArrayList<>();
        spinner= (SearchableSpinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        for(Material material : smaterials) {
            if(categoryId==material.getCategory().getCategoryId()) {
                spinnerArray.add(new Material(material.getMaterialID(), material.getMaterialName(), material.getUnitOfMeasure()));
            }
          }

        ArrayAdapter<Material> dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addmaterial(View v) throws JSONException {


        if(editMatQuantity.getText().length()==0 ) {
            editMatQuantity.setError("enter quantity");

        }
        else {
            MaterialList materialList = new MaterialList();
            materialList.setMaterialID(materialId);
            materialList.setMaterialName(materialName);
            materialList.setUnitOfMeasure(unitOfMeasure);
            materialList.setInQty(Double.parseDouble(editMatQuantity.getText().toString()));

            materials.add(materialList);


            //editMaterialId.setText("");
            editMatQuantity.setText("");
            textUnitOfMeasure.setText("");

            showList();
        }
    }

    private void showList(){

        adapter=new AddInwardAdapter(this,materials);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    public void submit(View v){
        if(!materials.isEmpty()) {
            Inward inward = new Inward();
            inward.setWarehouseId(warehouseId);
            inward.setOrganisationId(MainActivity._orgId);

            InwardDetails inwardDetails = new InwardDetails();
            inwardDetails.setMaterialList(materials);

            RootObject rootObject = new RootObject();
            rootObject.setInward(inward);
            rootObject.setInwardDetails(inwardDetails);


            InwardAPI api = retrofit.create(InwardAPI.class);

            Call<ResponseBody> call = api.createInward(rootObject);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AddInwardActivity.this, "Inward generated successfully...", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(AddInwardActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please add inwards to list....");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Material material = (Material) parent.getSelectedItem();
        materialName=material.getMaterialName();
        materialId=material.getMaterialID();
        unitOfMeasure=material.getUnitOfMeasure();
        textUnitOfMeasure.setText(unitOfMeasure);

 }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
