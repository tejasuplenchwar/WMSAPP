package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Contractor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 02/02/2017.
 */

public interface ContractorAPI {
    @GET("contractor/contractorDetailsJson")
    Call<ArrayList<Contractor>> getContractors();

    @GET("contractor/contractorByOrgId/{orgId}")
    Call<ArrayList<Contractor>> getContractors(@Path("orgId") String orgId);


    @POST("contractor/addCtr")
    Call<ResponseBody> createContractor(@Body Contractor contractor);


    @POST("contractor/editCtr")
    Call<ResponseBody> editContractor(@Body Contractor contractor);

    @POST("contractor/delete/ctr")
    Call<ResponseBody> deleteContractor(@Body Contractor contractor);


   /* @GET("warehouse/warehouseByOrgId/{orgId}")
    Call<ArrayList<Warehouse>> getWarehouses(@Path("orgId") String orgId);*/
}
