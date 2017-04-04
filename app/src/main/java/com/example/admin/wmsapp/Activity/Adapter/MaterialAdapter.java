package com.example.admin.wmsapp.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.wmsapp.Activity.Model.Material;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by admin on 06/02/2017.
 */

public class MaterialAdapter extends ArrayAdapter<Material> {

    private final Context context;
    private final ArrayList<Material> materials;

    public MaterialAdapter(Context context, ArrayList<Material> materials) {
        super(context, 0);
        this.context=context;
        this.materials=materials;

    }

    @Nullable
    @Override
    public Material getItem(int position) {
        return super.getItem(getCount()-position-1);
    }

    @Override
    public int getCount() {
        return materials.size();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Material material=materials.get(position);
        LinearLayout layout=null;
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.material_list_item, null);
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
        TextView textMaterialCode=(TextView)layout.findViewById(R.id.textMaterialCode);
        TextView unitOfMeasure=(TextView)layout.findViewById(R.id.unitOfMeasure);
        TextView unitPrice=(TextView)layout.findViewById(R.id.unitPrice);

        textMaterialName.setText(material.getMaterialName());
        if(material.getMaterialCode()!=null) {
            textMaterialCode.setText(material.getMaterialCode());
        }unitOfMeasure.setText(material.getUnitOfMeasure());
        unitPrice.setText(String.valueOf(material.getUnitPrice()));



        return layout;
    }
}
