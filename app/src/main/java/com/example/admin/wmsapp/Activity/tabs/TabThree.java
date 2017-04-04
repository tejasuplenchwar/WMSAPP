package com.example.admin.wmsapp.Activity.tabs;

import android.os.Bundle;

import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.wmsapp.Activity.Services.OrderAPI;
import com.example.admin.wmsapp.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/**
 * Created by admin on 17/02/2017.
 */

public class TabThree extends Fragment {

    ArrayList<Object[]>mAllData=new ArrayList<>();
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.tab3, container, false);
        getTopBuyer();
        return view;
    }

    private void getTopBuyer(){
        OrderAPI api=retrofit.create(OrderAPI.class);

        Call<ArrayList<Object[]>> call=api.getTopBuyer();

        call.enqueue(new Callback<ArrayList<Object[]>>() {
            @Override
            public void onResponse(Call<ArrayList<Object[]>> call, Response<ArrayList<Object[]>> response) {

               if(response.body()!=null){
                   mAllData.addAll(response.body());
                   showHBarChart();
               }

            }

            @Override
            public void onFailure(Call<ArrayList<Object[]>> call, Throwable t) {

            }
        });
    }

    private void showHBarChart(){
        int i=0;
        for(Object[]row:mAllData){
            entries.add(new BarEntry(Float.parseFloat(row[0].toString()),i));
            labels.add(row[2].toString());
            i++;
        }

        HorizontalBarChart horizontalBarChart= (HorizontalBarChart) getActivity().findViewById(R.id.hChart);

        BarDataSet dataset=new BarDataSet(entries,"# of Contractors");
        BarData data = new BarData(labels, dataset);

        dataset.setColors(ColorTemplate.JOYFUL_COLORS); //
        horizontalBarChart.setData(data);
        horizontalBarChart.fitScreen();
        horizontalBarChart.setFitsSystemWindows(true);
        horizontalBarChart.setKeepScreenOn(true);


        horizontalBarChart.animateY(1000);
    }
}
