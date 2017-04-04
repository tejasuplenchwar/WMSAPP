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

import com.example.admin.wmsapp.Activity.RootObject.OrderList;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/02/2017.
 */

public class AddOrderAdapter extends ArrayAdapter<OrderList> {
    private final Context context;
    private List<OrderList> orders;

    public AddOrderAdapter(Context context, List<OrderList> orders) {
        super(context, 0);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout=null;
        OrderList orderList=orders.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.ordermat_list_item, null);
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

        TextView textMaterialName= (TextView) layout.findViewById(R.id.textMaterialName);
        TextView unitOfMeasure= (TextView) layout.findViewById(R.id.unitOfMeasure);
        TextView orderQty= (TextView) layout.findViewById(R.id.orderQty);

       textMaterialName.setText(orderList.getMaterialName());
        unitOfMeasure.setText(orderList.getUnitMeasure());
        orderQty.setText(""+orderList.getOrderQty());



        return layout;

    }
}
