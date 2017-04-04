package com.example.admin.wmsapp.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.wmsapp.Activity.Model.Warehouse;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;

/**
 * Created by admin on 07/02/2017.
 */

public class WarehouseAdapter extends ArrayAdapter<Warehouse> {
    private final Context context;
    private final ArrayList<Warehouse> warehouses;


    public WarehouseAdapter(Context context, ArrayList<Warehouse> warehouses) {
        super(context, 0);
        this.context=context;
        this.warehouses=warehouses;
    }

    @Override
    public int getCount() {
       return warehouses.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Warehouse warehouse=warehouses.get(position);

        LinearLayout layout=null;
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.warehouse_list_item, null);
        }
        else
        {
            layout=(LinearLayout)convertView;
        }

        if ((position % 2) == 0) {
            layout.setBackgroundColor(Color.parseColor("#f6f6f5"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#fbfbfb"));
        }

        TextView textWarehouseName=(TextView)layout.findViewById(R.id.textWarehouseName);
        TextView textWarehouseLoc=(TextView)layout.findViewById(R.id.textWarehouseLoc);
        TextView textWarehouseRegion=(TextView)layout.findViewById(R.id.textWarehouseRegion);

        textWarehouseName.setText(warehouse.getWarehouseName());
        textWarehouseRegion.setText(warehouse.getwRegion());
        textWarehouseLoc.setText(warehouse.getWarehouseLoc());

        return layout;
    }
}
