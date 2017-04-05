package com.example.admin.wmsapp.Activity.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.R;
import com.squareup.picasso.Picasso;


public class MaterialDetailsActivity extends AppCompatActivity {
 TextView textCategory,textMaterialName,textMaterialCode,textMatDesc,textUOM,textUnitPrice;
   ImageView materialImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_details);

        setTitle("Material Details");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        textCategory= (TextView) findViewById(R.id.textCategory);
        textMaterialName= (TextView) findViewById(R.id.textMaterialName);
        textMaterialCode= (TextView) findViewById(R.id.textMaterialCode);
        textMatDesc= (TextView) findViewById(R.id.textMatDesc);
        textUOM= (TextView) findViewById(R.id.textUOM);
        textUnitPrice= (TextView) findViewById(R.id.textUnitPrice);

        Material material= (Material) getIntent().getSerializableExtra("Material");

        textCategory.setText(material.getCategory().getCategoryName());
        textMaterialName.setText(material.getMaterialName());
        textMaterialCode.setText(material.getMaterialCode());
        textMatDesc.setText(material.getMaterialDesc());
        textUOM.setText(material.getUnitOfMeasure());
        textUnitPrice.setText(""+material.getUnitPrice());
        Log.d("",""+material);
        materialImage= (ImageView) findViewById(R.id.materialImage);


        Picasso.with(this)
                .load("devsrv03-pun:1339/"+material.getMaterialImage())
                .resize(150, 150)
                .into(materialImage);
   }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
