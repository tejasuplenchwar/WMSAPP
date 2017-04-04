package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Fcm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 07/03/2017.
 */

public interface FcmAPI {
    @POST("fcm/addToken")
    Call<ResponseBody> createFcm(@Body Fcm fcm);
}
