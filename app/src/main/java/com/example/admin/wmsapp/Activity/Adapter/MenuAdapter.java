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

import com.example.admin.wmsapp.Activity.Model.Menu;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;

/**
 * Created by admin on 04/04/2017.
 */

public class MenuAdapter extends ArrayAdapter<Menu> {
    private final Context context;
    private final ArrayList<Menu> menuList;

    public MenuAdapter(Context context,ArrayList<Menu> menuList) {
        super(context, 0);
        this.context=context;
        this.menuList=menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Menu menu=menuList.get(position);
        LinearLayout layout=null;
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.menu_list_item, null);
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
        TextView textMenuId= (TextView) layout.findViewById(R.id.textMenuId);
        TextView textMenuName= (TextView) layout.findViewById(R.id.textMenuName);
        TextView textMenuIcon= (TextView) layout.findViewById(R.id.textMenuIcon);

        textMenuId.setText("MenuId-"+menu.getMenu_id());
        textMenuName.setText(menu.getMenu_name());
        textMenuIcon.setText(menu.getMenu_icon());
        return layout;
    }
}
