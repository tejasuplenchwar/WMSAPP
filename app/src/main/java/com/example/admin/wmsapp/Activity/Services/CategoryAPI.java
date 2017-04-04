package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Category;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 31/03/2017.
 */

public interface CategoryAPI {

    @GET("category/categoryByOrgId/{orgId}")
    Call<ArrayList<Category>> getAllCategory(@Path("orgId") String orgId);

    @POST("category/add_ctgry")
    Call<ResponseBody> createCategory(@Body Category category);

    @POST("category/editCtgry")
    Call<ResponseBody> editCategory(@Body Category category);

    @POST("category//delete/ctgry")
    Call<ResponseBody> deleteCategory(@Body Category category);


}
