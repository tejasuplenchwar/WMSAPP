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

import com.example.admin.wmsapp.Activity.Model.Inventory;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;

/**
 * Created by admin on 08/02/2017.
 */

public class InventoryAdapter extends ArrayAdapter<Inventory>{
    private final Context context;
    private final ArrayList<Inventory> inventories;

    public InventoryAdapter(Context context, ArrayList<Inventory> inventories) {
        super(context, 0);
        this.context=context;
        this.inventories=inventories;
    }

    @Override
    public int getCount() {
        return inventories.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Inventory inventory=inventories.get(position);
        LinearLayout layout=null;
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.inventory_list_item, null);
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

        TextView textMaterialName=(TextView)layout.findViewById(R.id.textMaterialName);
        TextView textWarehouseName=(TextView)layout.findViewById(R.id.textWarehouseName);
        TextView unitPrice=(TextView)layout.findViewById(R.id.unitPrice);
        TextView totalQuantity=(TextView)layout.findViewById(R.id.totalQuantity);
        TextView availableQuantity=(TextView)layout.findViewById(R.id.availableQuantity);

        textMaterialName.setText(inventory.getMaterial().getMaterialName());
        textWarehouseName.setText(inventory.getWarehouse().getWarehouseName());
        unitPrice.setText(" "+inventory.getMaterial().getUnitPrice());
        totalQuantity.setText(" "+inventory.getTotalQty());
        availableQuantity.setText(" "+inventory.getAvailableQty());
        return layout;
    }
}
