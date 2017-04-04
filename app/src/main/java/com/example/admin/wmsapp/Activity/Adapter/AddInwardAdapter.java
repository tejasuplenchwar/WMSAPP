package com.example.admin.wmsapp.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.wmsapp.Activity.RootObject.MaterialList;
import com.example.admin.wmsapp.R;

import java.util.List;

/**
 * Created by admin on 15/02/2017.
 */

public class AddInwardAdapter extends ArrayAdapter<MaterialList>{
   private final Context context;
    private final List<MaterialList> materials;
    public AddInwardAdapter(Context context,List<MaterialList> materials) {
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
        MaterialList materialList=materials.get(position);

        if(convertView==null) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.inmaterial_list_item, null);
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
        TextView inQty= (TextView) layout.findViewById(R.id.inQty);

        textMaterialName.setText(materialList.getMaterialName());
        unitOfMeasure.setText(materialList.getUnitOfMeasure());
        inQty.setText(""+materialList.getInQty());

        return layout;
    }
}
