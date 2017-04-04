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

import com.example.admin.wmsapp.Activity.Model.Inward;
import com.example.admin.wmsapp.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 08/02/2017.
 */

public class InwardAdapter extends ArrayAdapter<Inward> {
    private final Context context;
    private final ArrayList<Inward> inwards;
    public InwardAdapter(Context context, ArrayList<Inward> inwards) {
        super(context, 0);
        this.context=context;
        this.inwards=inwards;
    }

    @Override
    public int getCount() {
        return inwards.size();

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout=null;
        Inward inward=inwards.get(position);

        if(convertView==null) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.inward_list_item, null);
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

        TextView textInwardDate=(TextView)layout.findViewById(R.id.textInwardDate);
        TextView textInwardWarehouse=(TextView)layout.findViewById(R.id.textInwardWarehouse);
        TextView textInwardId=(TextView)layout.findViewById(R.id.textInwardId);
        Date date=new Date(inward.getInDate());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate= formatter.format(date);
        textInwardDate.setText(strDate);
        textInwardWarehouse.setText(inward.getWarehouse().getWarehouseName());
        textInwardId.setText("InwardId-"+inward.getInId());

        return layout;
    }
}
