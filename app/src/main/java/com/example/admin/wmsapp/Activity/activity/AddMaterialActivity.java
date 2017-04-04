package com.example.admin.wmsapp.Activity.activity;


import android.app.Activity;

import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.RequestQueue;

import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;

import com.android.volley.toolbox.Volley;
import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.Activity.Model.ImageFilePath;
import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.Services.MaterialAPI;
import com.example.admin.wmsapp.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;




public class AddMaterialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText editMaterialName,editMaterialDesc,editUnitPrice,editMaterialCode;
    Spinner spinner;

    ImageView materialImage;
    Bitmap imageBitmap;
    String item;
    String []file;
    SearchableSpinner spinnerSearch;
    String selectedImagePath;
    ArrayList<Category> categories=new ArrayList<>();
    Material material = new Material();

    String material_image,imageName;
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    String upLoadUrl = "http://192.168.100.8:1339/users/materialUpload";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        setTitle("Add Material");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        editMaterialCode= (EditText) findViewById(R.id.editMaterialCode);
        editMaterialName=(EditText)findViewById(R.id.editMaterialName);
        editMaterialDesc=(EditText)findViewById(R.id.editMaterialDesc);
       // editUnitOfMeasure=(EditText)findViewById(R.id.editUnitOfMeasure);
        editUnitPrice=(EditText)findViewById(R.id.editUnitPrice);
        spinnerSearch= (SearchableSpinner) findViewById(R.id.spinnerSearch);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());

        materialImage= (ImageView) findViewById(R.id.materialImage);

        materialImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);

            }
        });
        spinner=(Spinner)findViewById(R.id.spinner);


        spinner.setOnItemSelectedListener(this);

        List<String> measures=new ArrayList<String>();
        measures.add("Numbers");
        measures.add("Dozons");
        measures.add("Kilogram");
        measures.add("Pieces");
        measures.add("Meters");
        measures.add("Liters");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, measures);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

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
        spinnerSearch.setAdapter(categoryAdapter);
        spinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category= (Category) parent.getSelectedItem();
                material.setCategory(category);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

 }


     public void imageUpload(View v){

         imageUpload(filePath);

     }
@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(View v) {
        if(editMaterialName.getText().length()==0)
        {
            editMaterialName.setError("enter material name");
        }
        else if(editMaterialDesc.getText().length()==0)
        {
            editMaterialDesc.setError("enter material desc");
        }
        else if(editUnitPrice.getText().length()==0){
            editUnitPrice.setError("enter unit price");
        }
        else if(editMaterialCode.getText().length()==0){
            editMaterialCode.setError("enter material code");
        }
        else if(materialImage.getDrawable()==null){
            Toast.makeText(this, "please select image first", Toast.LENGTH_SHORT).show();
        }
        else {


            material.setMaterialName(editMaterialName.getText().toString());
            material.setMaterialDesc(editMaterialDesc.getText().toString());
            material.setUnitOfMeasure(item);
            material.setUnitPrice(Double.parseDouble(editUnitPrice.getText().toString()));
            material.setOrganisationId(MainActivity._orgId);
            material.setMaterialCode(editMaterialCode.getText().toString());
            material.setMaterialImage(material_image);
            System.out.println(material);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MaterialAPI api = retrofit.create(MaterialAPI.class);
            Call<ResponseBody> call = api.createMaterial(material);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AddMaterialActivity.this, "Material added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                Log.d("picUri", picUri.toString());
                Log.d("filePath", filePath);

                materialImage.setImageURI(picUri);

            }

        }

    }
    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


public void getFileName(String imageName){
    material_image=imageName;

}
    private void imageUpload(final String imagePath) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(com.android.volley.Request.Method.POST, upLoadUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                       String fileName=response;
                       String []file=fileName.split(":");
                        Log.d("Response", file[1]);
                        imageName=file[1];
                        getFileName(imageName);
                       /* try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        smr.addFile("file", imagePath);
        MyApplication.getInstance().addToRequestQueue(smr);

    }
}
