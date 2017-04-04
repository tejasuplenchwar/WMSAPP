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

import com.example.admin.wmsapp.Activity.Model.InwardDetails;
import com.example.admin.wmsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 09/02/2017.
 */

public class InwardDetailsAdapter extends ArrayAdapter<InwardDetails> {
    private final Context context;
    private final ArrayList<InwardDetails> inwardDetails;

    public InwardDetailsAdapter(Context context, ArrayList<InwardDetails> inwardDetails) {
        super(context, 0);
        this.context=context;
        this.inwardDetails=inwardDetails;
    }


    @Override
    public int getCount() {
        return inwardDetails.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout=null;
        InwardDetails inwardDetail=inwardDetails.get(position);

        if(convertView==null) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.inwarddetail_list_item, null);
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
        TextView textInwardId=(TextView)layout.findViewById(R.id.textInwardId);
        TextView textMaterialName=(TextView)layout.findViewById(R.id.textMaterialName);
        TextView textMaterialQuantity=(TextView)layout.findViewById(R.id.textMaterialQuantity);

        textMaterialName.setText(inwardDetail.getMaterial().getMaterialName());
        textMaterialQuantity.setText(""+inwardDetail.getInQty());
        textInwardId.setText(""+inwardDetail.getInward().getInId());
        Date date=new Date(inwardDetail.getInward().getInDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate= formatter.format(date);

        textInwardDate.setText(strDate);
        return layout;
    }
}
