package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Menu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 04/04/2017.
 */

public interface MenuAPI {
    @GET("menu/menuDetailsJson")
    Call<ArrayList<Menu>> getAllMenu();
}
