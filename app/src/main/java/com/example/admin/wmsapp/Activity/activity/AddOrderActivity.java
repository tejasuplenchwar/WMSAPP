package com.example.admin.wmsapp.Activity.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Adapter.AddOrderAdapter;

import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.Activity.Model.CategoryKey;
import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.RootObject.Order;
import com.example.admin.wmsapp.Activity.RootObject.OrderDetails;
import com.example.admin.wmsapp.Activity.RootObject.OrderList;
import com.example.admin.wmsapp.Activity.RootObject.OrderObject;
import com.example.admin.wmsapp.Activity.Services.ContractorAPI;
import com.example.admin.wmsapp.Activity.Services.InventoryAPI;
import com.example.admin.wmsapp.Activity.Services.MaterialAPI;
import com.example.admin.wmsapp.Activity.Services.OrderAPI;
import com.example.admin.wmsapp.Activity.Services.WarehouseAPI;
import com.example.admin.wmsapp.Activity.fragments.ContractorFragment;
import com.example.admin.wmsapp.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

public class AddOrderActivity extends AppCompatActivity {
    List<OrderList> orders=new ArrayList<>();
    ArrayList<Contractor> sContractors=new ArrayList<>();
    ArrayList<Warehouse> sWarehouses=new ArrayList<>();
    ArrayList<Inventory> sInventory=new ArrayList<>();

    ArrayList<Category> categoryArrayList=new ArrayList<>();
    AddOrderAdapter adapter;

    ListView listView;
    ArrayList<Inventory> inventories=new ArrayList<>();
    Set<Category> hs = new HashSet<>();
    Set<Material> ms = new HashSet<>();
    int warehouseId,materialId,contractorId;
    double availableQuantity;

    int count,count1,catId;
    String materialName,unitOfMeasure1;

    SearchableSpinner wSpinner,cSpinner,mSpinner,spinnerCategory;
    TextView unitOfMeasure,availableQty;
    EditText orderQuantity;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setTitle("Add Order");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        unitOfMeasure= (TextView) findViewById(R.id.unitOfMeasure);
        availableQty= (TextView) findViewById(R.id.availableQuantity);
        orderQuantity=(EditText)findViewById(R.id.orderQuantity);
        spinnerCategory= (SearchableSpinner) findViewById(R.id.spinnerCategory);
        listView= (ListView) findViewById(R.id.listView);
        spinnerWarehouse();
        spinnerContractors();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

            /*Warehouse Spinner item*/
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
                if(count>=1)
                {
                    wSpinner.setEnabled(false);
                }
                //Toast.makeText(AddOrderActivity.this, "warehouseId:-"+warehouseId, Toast.LENGTH_SHORT).show();
                getInventory();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void showCategory(){
        for(Inventory inventory:inventories){
            if(warehouseId==inventory.getWarehouse().getWarehouseId()) {
               categoryArrayList.add(inventory.getMaterial().getCategory());
            }
        }
      for(Category cat:categoryArrayList){
         if(!hs.contains(cat)) {
             hs.add(cat);
         }
      }
        categoryArrayList.clear();
        categoryArrayList.addAll(hs);




        ArrayAdapter<Category> categoryAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categoryArrayList);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category= (Category) parent.getSelectedItem();
                catId=category.getCategoryId();
                spinnerMaterial();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*Contractor Spinner Items*/
    private void spinnerContractors(){
        ContractorAPI api=retrofit.create(ContractorAPI.class);
        Call<ArrayList<Contractor>> call=api.getContractors();

        call.enqueue(new Callback<ArrayList<Contractor>>() {
            @Override
            public void onResponse(Call<ArrayList<Contractor>> call, Response<ArrayList<Contractor>> response) {
               if(response.body()!=null){
                   sContractors=response.body();
                   showContractors();
               }

            }

            @Override
            public void onFailure(Call<ArrayList<Contractor>> call, Throwable t) {

            }
        });
    }

    private void showContractors(){
        ArrayList<Contractor> spinnerContractor=new ArrayList<>();
        cSpinner= (SearchableSpinner) findViewById(R.id.cSpinner);

        for(Contractor contractor:sContractors){
         spinnerContractor.add(contractor);
        }
        ArrayAdapter<Contractor> dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerContractor);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cSpinner.setAdapter(dataAdapter);
        cSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Contractor contractor= (Contractor) parent.getSelectedItem();
                contractorId=contractor.getCtrId();
                count1++;
                if(count1==2)
                {
                    cSpinner.setEnabled(false);
                }
                //Toast.makeText(AddOrderActivity.this, "contractId:-"+contractorId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


      /*Material with respect to Warehouse Selected*/
    private void getInventory(){
        InventoryAPI api=retrofit.create(InventoryAPI.class);
        Call<ArrayList<Inventory>> call=api.getInventory();

        call.enqueue(new Callback<ArrayList<Inventory>>() {
            @Override
            public void onResponse(Call<ArrayList<Inventory>> call, Response<ArrayList<Inventory>> response) {

                if(response.body()!=null){
                    inventories=response.body();
                    showCategory();
                }



            }

            @Override
            public void onFailure(Call<ArrayList<Inventory>> call, Throwable t) {

            }
        });
    }

    private void spinnerMaterial(){
        mSpinner= (SearchableSpinner) findViewById(R.id.mSpinner);
        ArrayList<Material> sMaterials=new ArrayList<>();
        sMaterials.clear();
        ms.clear();
        for(Inventory inventory:inventories)
        {
            if(catId==inventory.getMaterial().getCategory().getCategoryId())
            {
                sMaterials.add(inventory.getMaterial());
                sInventory.add(inventory);
            }
        }

        for(Material mat:sMaterials){
            if(!ms.contains(mat)) {
                ms.add(mat);
            }
        }
        sMaterials.clear();
        sMaterials.addAll(ms);
        ArrayAdapter<Material> dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sMaterials);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Material material= (Material) parent.getSelectedItem();
                materialId=material.getMaterialID();
                materialName=material.getMaterialName();
                unitOfMeasure1=material.getUnitOfMeasure();
                unitOfMeasure.setText(material.getUnitOfMeasure());
                getAvailQuantity();

                //Toast.makeText(AddOrderActivity.this, "materialId:-"+materialId, Toast.LENGTH_SHORT).show();
               // Toast.makeText(AddOrderActivity.this, "availableQuantity"+availableQuantity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getAvailQuantity(){
        for(Inventory inventory:sInventory)
        {
            if(materialId==inventory.getMaterial().getMaterialID()){
                availableQuantity=inventory.getAvailableQty();
                availableQty.setText(""+availableQuantity);
            }
        }

        //Toast.makeText(this, "availableQuantity:-"+availableQuantity, Toast.LENGTH_SHORT).show();
    }

    public void orderMaterial(View v){
        if(orderQuantity.getText().length()==0)
        {
            orderQuantity.setError("enter order quantity");

        }
       else if(availableQuantity<Double.parseDouble(orderQuantity.getText().toString())){

            orderQuantity.setError("please see available quantity first");
        }
        else {
            OrderList orderList = new OrderList();
            orderList.setMaterialId(materialId);
            orderList.setMaterialName(materialName);
            orderList.setUnitMeasure(unitOfMeasure1);
            orderList.setOrderQty(Double.parseDouble(orderQuantity.getText().toString()));

            orders.add(orderList);

            showList();

            orderQuantity.setText("");
        }
    }

    private void showList(){


            adapter=new AddOrderAdapter(this,orders);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

    public void submit(View v) {
        if (!orders.isEmpty()) {

            Order order = new Order();
            order.setWarehouseId(warehouseId);
            order.setContractorID(contractorId);
            order.setOrganisationId(MainActivity._orgId);

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderList(orders);

            OrderObject orderObject = new OrderObject();
            orderObject.setOrder(order);
            orderObject.setOrderDetails(orderDetails);

            OrderAPI api = retrofit.create(OrderAPI.class);
            Call<ResponseBody> call = api.createOrder(orderObject);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AddOrderActivity.this, "Order generated successfully....!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(AddOrderActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please add orders to list....");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }


}
