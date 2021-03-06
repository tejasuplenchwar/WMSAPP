package com.example.admin.wmsapp.Activity.tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Services.OrderAPI;
import com.example.admin.wmsapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/**
 * Created by admin on 17/02/2017.
 */

public class TabTwo extends Fragment {
    ArrayList<Object[]> mAllData=new ArrayList<>();

    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.tab2, container, false);
        getTopSeller();

        return view;
    }



    private void getTopSeller(){
        OrderAPI api=retrofit.create(OrderAPI.class);
        Call<ArrayList<Object[]>> call=api.getTopSeller();
        call.enqueue(new Callback<ArrayList<Object[]>>() {
            @Override
            public void onResponse(Call<ArrayList<Object[]>> call, Response<ArrayList<Object[]>> response) {

                if( response.body()!=null) {
                    mAllData.addAll(response.body());
                    showBarChart();
                }
                 System.out.println(mAllData);

            }

            @Override
            public void onFailure(Call<ArrayList<Object[]>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBarChart(){


        int i=0;
        for(Object[]row:mAllData){
            System.out.println(row[0].toString());
            System.out.println(row[1].toString());
            entries.add(new BarEntry(Float.parseFloat(row[0].toString()),i));
            labels.add(row[1].toString());
            i++;
        }

      BarChart barChart;
        barChart = (BarChart) getActivity().findViewById(R.id.bChart);

        BarDataSet dataset = new BarDataSet(entries, "# of Warehouse");
        BarData data = new BarData(labels, dataset);

        dataset.setColors(ColorTemplate.JOYFUL_COLORS); //
        dataset.setHighlightEnabled(true); // allow highlighting for DataSet

        barChart.setData(data);
        barChart.fitScreen();
        barChart.setFitsSystemWindows(true);
        barChart.setKeepScreenOn(true);
        barChart.setScaleXEnabled(true);

       barChart.animateY(1500);
    }
}
