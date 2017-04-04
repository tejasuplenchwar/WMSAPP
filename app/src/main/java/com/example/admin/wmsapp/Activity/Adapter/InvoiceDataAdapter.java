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

import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.Activity.RootObject.MaterialList;
import com.example.admin.wmsapp.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by admin on 14/03/2017.
 */

public class InvoiceDataAdapter extends ArrayAdapter<Material> {

    DecimalFormat precision=new DecimalFormat("0.00");
    private final Context context;
    private final List<Material> materials;

    public InvoiceDataAdapter(Context context,List<Material> materials) {
        super(context, 0);
        this.context=context;
        this.materials=materials;
    }

    @Override
    public int getCount() {
        return materials.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout=null;
        Material materialList=materials.get(position);

        if(convertView==null) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.gen_inv_item, null);
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
        TextView textUnitPrice=(TextView)layout.findViewById(R.id.textUnitPrice);
        TextView textMaterialQty=(TextView)layout.findViewById(R.id.textMaterialQty);


        textMaterialName.setText(materialList.getMaterialName());
        textUnitPrice.setText(precision.format(materialList.getUnitPrice()));
        textMaterialQty.setText(precision.format(materialList.getOrderQty()));

        return layout;
    }
}
