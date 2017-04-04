package com.example.admin.wmsapp.Activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Fcm;
import com.example.admin.wmsapp.Activity.Services.FcmAPI;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.Activity.tabs.Pager;
import com.example.admin.wmsapp.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/**
 * Created by admin on 14/02/2017.
 */

public class DashboardFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Inventory Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Tab Title"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Tab Title"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager)view.findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        getAppToken();



        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Dashboard");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void getAppToken(){
        FirebaseMessaging.getInstance().subscribeToTopic("Test");
        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d("ABC","RefreshedToken:- "+token);

        /*TelephonyManager mngr = (TelephonyManager)getSystemService(MainActivity.TELEPHONY_SERVICE);
        mngr.getDeviceId();*/
        //Log.d("telephonyNo","IMEI:- "+mngr.getDeviceId());
        //Toast.makeText(this, "token:-"+token, Toast.LENGTH_SHORT).show();
        Fcm fcm=new Fcm();
        fcm.setToken_id(token);
        // fcm.setImei( mngr.getDeviceId());
        addToken(fcm);

    }

    private void addToken(Fcm fcm){
        TelephonyManager mngr = (TelephonyManager)getActivity().getSystemService(getContext().TELEPHONY_SERVICE);
        mngr.getDeviceId();
        fcm.setImei(mngr.getDeviceId());
        fcm.setContractor_email(MainActivity.email);
        FcmAPI api=retrofit.create(FcmAPI.class);
        Call<ResponseBody> call=api.createFcm(fcm);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               Toast.makeText(getActivity(), "Token Saved to database", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
