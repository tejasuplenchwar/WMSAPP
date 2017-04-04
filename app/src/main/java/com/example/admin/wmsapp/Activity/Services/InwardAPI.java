package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.Activity.Model.Inward;
import com.example.admin.wmsapp.Activity.Model.InwardDetails;
import com.example.admin.wmsapp.Activity.RootObject.RootObject;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 08/02/2017.
 */

public interface InwardAPI {

    @GET("inward/viewInwardJson")
    Call<ArrayList<Inward>> getInwards();

    @GET("inward/inwardByOrgId/{orgId}")
    Call<ArrayList<Inward>> getInwards(@Path("orgId") String orgId);

    @GET("inward/inwardDetailsJson")
    Call<ArrayList<InwardDetails>> getInwardDetails();


    @POST("inward/add_inward")
    Call<ResponseBody> createInward(@Body RootObject rootObject);
}


