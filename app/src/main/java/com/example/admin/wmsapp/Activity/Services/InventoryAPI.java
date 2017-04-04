package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.Activity.Model.TopSeller;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by admin on 08/02/2017.
 */

public interface InventoryAPI {

    @GET("inventory/inventoryDetailsJson")
    Call<ArrayList<Inventory>> getInventory();

    @GET("inventory/inventoryByOrgId/{orgId}")
    Call<ArrayList<Inventory>> getInventory(@Path("orgId") String orgId);


}
