package com.example.admin.wmsapp.Activity.tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.Activity.Services.InventoryAPI;
import com.example.admin.wmsapp.Activity.Services.WarehouseAPI;
import com.example.admin.wmsapp.R;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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

public class TabOne extends Fragment {
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<Inventory> mAllData=new ArrayList<>();
    ArrayList<Warehouse> sWarehouses=new ArrayList<>();
    Spinner wSpinner;
    int warehouseId;
    private SeekBar mSeekBarX, mSeekBarY;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab1, container, false);
        spinnerWarehouse();

        return view;
    }

    private void spinnerWarehouse(){
        WarehouseAPI api=retrofit.create(WarehouseAPI.class);
        Call<ArrayList<Warehouse>> call=api.getWarehouses();
        call.enqueue(new Callback<ArrayList<Warehouse>>() {
            @Override
            public void onResponse(Call<ArrayList<Warehouse>> call, Response<ArrayList<Warehouse>> response) {
                sWarehouses=response.body();
                showWarehouse();
            }

            @Override
            public void onFailure(Call<ArrayList<Warehouse>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error-"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWarehouse(){
        ArrayList<Warehouse> spinnerWarehouse=new ArrayList<>();
        wSpinner= (Spinner) getActivity().findViewById(R.id.wSpinner);
        for(Warehouse warehouse : sWarehouses) {
            spinnerWarehouse.add(new Warehouse(warehouse.getWarehouseId(),warehouse.getWarehouseName()));
        }
        ArrayAdapter<Warehouse> dataAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,spinnerWarehouse);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        wSpinner.setAdapter(dataAdapter);
        wSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Warehouse warehouse = (Warehouse) parent.getSelectedItem();
                warehouseId=warehouse.getWarehouseId();

                entries.clear();
                labels.clear();
                getInventory();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getInventory(){
        InventoryAPI api=retrofit.create(InventoryAPI.class);
        Call<ArrayList<Inventory>> call=api.getInventory();

        call.enqueue(new Callback<ArrayList<Inventory>>() {
            @Override
            public void onResponse(Call<ArrayList<Inventory>> call, Response<ArrayList<Inventory>> response) {
                if(response.body()!=null){
                    mAllData=response.body();
                    showPieChart();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Inventory>> call, Throwable t) {

            }
        });
    }

    private void showPieChart (){


            for(Inventory inventory:mAllData)
            {
                if(warehouseId==inventory.getWarehouse().getWarehouseId())
                {
                    Log.d("TabOne","MaterialName"+inventory.getMaterial().getMaterialName());
                   entries.add(new Entry(inventory.getAvailableQty(),inventory.getMaterial().getMaterialID()));
                    labels.add(inventory.getMaterial().getMaterialName());
                }

            }


        PieChart pieChart = (PieChart)getActivity().findViewById(R.id.chart);

        PieDataSet dataset = new PieDataSet(entries, "# of Materials");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS); //
        dataset.setSliceSpace(1f);
        dataset.setSelectionShift(5f);
        pieChart.setDescription("Description");
        pieChart.setData(data);
        pieChart.setTouchEnabled(true);

        Legend legend=pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setMaxSizePercent(0.95f);

       // pieChart.setHoleRadius(25);


        pieChart.animateY(800);

    }


}
