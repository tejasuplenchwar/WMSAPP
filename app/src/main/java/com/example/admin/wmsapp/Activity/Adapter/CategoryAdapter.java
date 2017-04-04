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

import com.example.admin.wmsapp.Activity.Model.Category;
import com.example.admin.wmsapp.R;

import java.util.List;

/**
 * Created by admin on 31/03/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        super(context, 0);
        this.categoryList=categoryList;
        this.context = context;
    }

    @Override
    public int getCount() {
      return  categoryList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        Category category=categoryList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.category_list_item, null);
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

        TextView textCategoryId= (TextView) layout.findViewById(R.id.textCategoryId);
        TextView textCategoryName= (TextView) layout.findViewById(R.id.textCategoryName);
        TextView textCategoryDesc= (TextView) layout.findViewById(R.id.textCategoryDesc);

        textCategoryId.setText("CategoryId-"+category.getCategoryId());
        textCategoryName.setText(category.getCategoryName());
        textCategoryDesc.setText(category.getCategoryDesc());

        return layout;

    }
}
