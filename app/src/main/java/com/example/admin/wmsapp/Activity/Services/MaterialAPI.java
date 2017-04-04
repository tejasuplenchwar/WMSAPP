package com.example.admin.wmsapp.Activity.Services;

import android.graphics.Bitmap;

import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.Activity.Model.Material;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 02/02/2017.
 */

public interface MaterialAPI {
    @GET("material/materialDetailsJson")
    Call<ArrayList<Material>> getMaterials();


    @POST("material/add_mtr")
    Call<ResponseBody> createMaterial(@Body Material material);

    @POST("material/delete/mtr")
    Call<ResponseBody> deleteMaterial(@Body Material material);

    @POST("material/editMtr")
    Call<ResponseBody> editMaterial(@Body Material material);

    @FormUrlEncoded
    @POST("users/materialUpload")
    Call<String> uploadImage(@Field("file")  String encodedImage);

    @GET("category/categoryByOrgId/{orgId}")
    Call<ArrayList<Category>> getAllCategory(@Path("orgId") String orgId);

}
