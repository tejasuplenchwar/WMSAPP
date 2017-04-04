package com.example.admin.wmsapp.Activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.admin.wmsapp.R;
import com.squareup.picasso.Picasso;

public class Imagedownload extends AppCompatActivity {

ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedownload);
        imageView= (ImageView) findViewById(R.id.imageView_details);

        Picasso.with(this)
                .load("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png")
                .resize(150, 150)
                .into(imageView);
    }
}
