package com.example.admin.wmsapp.Activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.wmsapp.R;

import java.util.Date;

import static com.example.admin.wmsapp.Activity.Model.Constants.EXPTOKEN;

public class SplashActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS = 0;
    long exp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        permissions();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
              startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }).start();*/
    }

    private void permissions() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // only for gingerbread and newer versions
            String permission = android.Manifest.permission.READ_PHONE_STATE;
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{
                                android.Manifest.permission.READ_PHONE_STATE,
                                android.Manifest.permission.INTERNET,
                                android.Manifest.permission.ACCESS_NETWORK_STATE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        },
                        MY_PERMISSIONS);
            }
            else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2000);

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                            String exptoken= preferences.getString(EXPTOKEN,"");
                            Log.d("",""+exptoken);
                            if(!exptoken.equals("")) {
                                exp = Long.parseLong(exptoken);
                            }
                            Date d=new Date();
                            long t=d.getTime();
                            Log.d("",""+t);
                            if(t>exp){
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            }
                            else{
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }
                           /* if (!preferences.getBoolean(LoginActivity.LOGIN_STATUS, false)) {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            } else {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }*/
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                       /* startActivity(new Intent(SplashActivity.this, LoginActivity.class));*/
                    }
                }).start();
            }
        }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                        String exptoken= preferences.getString(EXPTOKEN,"");
                        Log.d("",""+exptoken);
                        if(!exptoken.equals("")) {
                         exp = Long.parseLong(exptoken);
                        }Date d=new Date();
                        long t=d.getTime();
                         t=t/1000;
                        if(t>exp){
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                        else{
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                        Log.d("",""+t);
                       /* if (!preferences.getBoolean(LoginActivity.LOGIN_STATUS, false)) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }*/
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   /* startActivity(new Intent(SplashActivity.this, LoginActivity.class));*/
                }
            }).start();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                                String exptoken= preferences.getString(EXPTOKEN,"");
                                Log.d("",""+exptoken);
                                if(!exptoken.equals("")) {
                                    exp = Long.parseLong(exptoken);
                                }
                                Date d=new Date();
                                long t=d.getTime();
                                Log.d("",""+t);
                                if(t>exp){
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                }
                                else{
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                }
                               /* if (!preferences.getBoolean(LoginActivity.LOGIN_STATUS, false)) {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                } else {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                }*/
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                           /* startActivity(new Intent(SplashActivity.this, LoginActivity.class));*/
                        }
                    }).start();
                } else {
                    Toast.makeText(getApplication(), "Permission Granted",
                            Toast.LENGTH_LONG).show();
                    reload();
                }
                return;
            }
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
