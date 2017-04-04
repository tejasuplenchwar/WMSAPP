package com.example.admin.wmsapp.Activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Fcm;
import com.example.admin.wmsapp.Activity.Services.FcmAPI;
import com.example.admin.wmsapp.Activity.fragments.CategoryFragment;
import com.example.admin.wmsapp.Activity.fragments.ContractorFragment;
import com.example.admin.wmsapp.Activity.fragments.DashboardFragment;
import com.example.admin.wmsapp.Activity.fragments.InventoryFragment;
import com.example.admin.wmsapp.Activity.fragments.InviteFragment;
import com.example.admin.wmsapp.Activity.fragments.InvoiceFragment;
import com.example.admin.wmsapp.Activity.fragments.InwardFragment;
import com.example.admin.wmsapp.Activity.fragments.MaterialFragment;
import com.example.admin.wmsapp.Activity.fragments.MyProfileFragment;
import com.example.admin.wmsapp.Activity.fragments.OrderFragment;
import com.example.admin.wmsapp.Activity.fragments.RoleMenuFragment;
import com.example.admin.wmsapp.Activity.fragments.WarehouseFragment;
import com.example.admin.wmsapp.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.EMAIL;
import static com.example.admin.wmsapp.Activity.Model.Constants.NAME;
import static com.example.admin.wmsapp.Activity.Model.Constants.ORGID;
import static com.example.admin.wmsapp.Activity.Model.Constants.ORGNAME;
import static com.example.admin.wmsapp.Activity.Model.Constants.ROLE;
import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_PERMISSIONS = 0;



    public static  String name;
    public static  String email;
    public static  String role;
    public static  String _orgId;
    public static  String _orgName;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

TextView textUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //permissions();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        name=preferences.getString(NAME,"");
        email=preferences.getString(EMAIL,"");
        role=preferences.getString(ROLE,"");
        _orgId=preferences.getString(ORGID,"");
        _orgName=preferences.getString(ORGNAME,"");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        textUserName= (TextView) header.findViewById(R.id.textUserName);
        textUserName.setText(email);
        navigationView.setCheckedItem(R.id.nav_dashboard);
        navigationView.setActivated(true);
        navigationView.setNavigationItemSelectedListener(this);




        if(MainActivity.role.equals("ADM")){
            navigationView.getMenu().findItem(R.id.nav_dashboard).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_order).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_inward).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_inventory).setVisible(false);
            displaySelectedScreen(R.id.nav_category);
        }
        if(MainActivity.role.equals("CTR")){
          navigationView.getMenu().findItem(R.id.nav_dashboard).setVisible(false);
          navigationView.getMenu().findItem(R.id.nav_category).setVisible(false);
          navigationView.getMenu().findItem(R.id.nav_material).setVisible(false);
          navigationView.getMenu().findItem(R.id.nav_warehouse).setVisible(false);
          navigationView.getMenu().findItem(R.id.nav_invite).setVisible(false);
          navigationView.getMenu().findItem(R.id.nav_contractor).setVisible(false);
          navigationView.getMenu().findItem(R.id.nav_inward).setVisible(false);
            displaySelectedScreen(R.id.nav_order);
        }
        if(MainActivity.role.equals("WHM")){
            navigationView.getMenu().findItem(R.id.nav_dashboard).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_category).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_material).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_warehouse).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_invite).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_contractor).setVisible(false);
            displaySelectedScreen(R.id.nav_dashboard);
        }
        if(MainActivity.role.equals("generic")){
            displaySelectedScreen(R.id.nav_dashboard);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        // getAppToken();
    }

    private void permissions() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // only for gingerbread and newer versions
            String permission = android.Manifest.permission.READ_PHONE_STATE;
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                android.Manifest.permission.READ_PHONE_STATE,
                                android.Manifest.permission.INTERNET,
                                android.Manifest.permission.ACCESS_NETWORK_STATE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        },
                        MY_PERMISSIONS);
            }
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

    public void getAppToken(){
        FirebaseMessaging.getInstance().subscribeToTopic("Test");
        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d("ABC","RefreshedToken:- "+token);

       /* TelephonyManager mngr = (TelephonyManager)getSystemService(MainActivity.TELEPHONY_SERVICE);
        mngr.getDeviceId();*/
        //Log.d("telephonyNo","IMEI:- "+mngr.getDeviceId());
        Toast.makeText(this, "token:-"+token, Toast.LENGTH_SHORT).show();
        Fcm fcm=new Fcm();
        fcm.setToken_id(token);
        // fcm.setImei( mngr.getDeviceId());
        addToken(fcm);

    }

    private void addToken(Fcm fcm){
        TelephonyManager mngr = (TelephonyManager)getSystemService(MainActivity.TELEPHONY_SERVICE);
        mngr.getDeviceId();
        fcm.setImei(mngr.getDeviceId());
        Log.d("fcm","fcm:-"+fcm);
        FcmAPI api=retrofit.create(FcmAPI.class);
        Call<ResponseBody> call=api.createFcm(fcm);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Token Saved to database", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public  void displaySelectedScreen(int itemId){

        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_inventory:
                fragment = new InventoryFragment();
                break;

            case R.id.nav_warehouse:
                fragment = new WarehouseFragment();
                break;

            case R.id.nav_material:
                fragment = new MaterialFragment();
                break;

            case R.id.nav_contractor:
                fragment = new ContractorFragment();
                break;

            case R.id.nav_inward:
                fragment = new InwardFragment();
                break;

            case R.id.nav_order:
                fragment = new OrderFragment();
                break;

            case R.id.nav_dashboard:
                fragment=new DashboardFragment();
                break;

            case R.id.nav_invoice:
                fragment=new InvoiceFragment();
                break;

            case R.id.nav_myprofile:
                fragment=new MyProfileFragment();
                break;

            case R.id.nav_category:
                fragment=new CategoryFragment();
                break;

            case R.id.nav_invite:
                fragment=new InviteFragment();
                break;

            case R.id.nav_rolemenu:
                fragment=new RoleMenuFragment();
                break;

      }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
