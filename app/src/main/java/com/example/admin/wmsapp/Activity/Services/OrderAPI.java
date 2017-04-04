package com.example.admin.wmsapp.Activity.Services;

import com.example.admin.wmsapp.Activity.Model.Order;
import com.example.admin.wmsapp.Activity.Model.OrderDetails;
import com.example.admin.wmsapp.Activity.Model.TopOrder;
import com.example.admin.wmsapp.Activity.RootObject.OrderObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 09/02/2017.
 */

public interface OrderAPI {
    @GET("order/viewOrderJson")
    Call<ArrayList<Order>> getOrders();


    @GET("order/orderByOrgId/{orgId}")
    Call<ArrayList<Order>> getOrders(@Path("orgId") String orgId);



    @GET("order/orderDetailsJson")
    Call<ArrayList<OrderDetails>> getOrderDetails();

    @POST("order/add_order")
    Call<ResponseBody> createOrder(@Body OrderObject orderObject);


    @GET("order/topSellerJson")
    Call<ArrayList<Object[]>> getTopSeller();

    @GET("order/topBuyerJson")
    Call<ArrayList<Object[]>> getTopBuyer();




}


