package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Warehouse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 03/02/2017.
 */

public interface WarehouseAPI {
    @GET("warehouse/warehouseDetailsJson")
    Call<ArrayList<Warehouse>> getWarehouses();

    @GET("warehouse/warehouseByOrgId/{orgId}")
    Call<ArrayList<Warehouse>> getWarehouses(@Path("orgId") String orgId);

    @POST("warehouse/add_wrh")
    Call<ResponseBody> createWarehouse(@Body Warehouse warehouse);

    @POST("warehouse/delete/wre")
    Call<ResponseBody> deleteWarehouse(@Body Warehouse warehouse);

    @POST("warehouse/editWre")
    Call<ResponseBody> editWarehouse(@Body Warehouse warehouse);


}
