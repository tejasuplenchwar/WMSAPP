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

import com.example.admin.wmsapp.Activity.Model.Order;
import com.example.admin.wmsapp.Activity.Model.OrderDetails;
import com.example.admin.wmsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 09/02/2017.
 */

public class OrderDetailsAdapter extends ArrayAdapter<OrderDetails> {
    private final Context context;
    private ArrayList<OrderDetails> orderDetails;

    public OrderDetailsAdapter(Context context,ArrayList<OrderDetails> orderDetails) {
        super(context, 0);
        this.context=context;
        this.orderDetails=orderDetails;
    }

    @Override
    public int getCount() {
        return orderDetails.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderDetails orderDetail=orderDetails.get(position);
        LinearLayout layout=null;
        if(convertView==null) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.orderdetails_list_item, null);
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
        TextView textOrderId=(TextView)layout.findViewById(R.id.textOrderId);
        TextView textIssueDate=(TextView)layout.findViewById(R.id.textIssueDate);
        TextView textMaterialQuantity=(TextView)layout.findViewById(R.id.textMaterialQuantity);


        textMaterialName.setText(orderDetail.getMaterial().getMaterialName());
        textMaterialQuantity.setText(""+orderDetail.getOrderQty());

        Date date=new Date(orderDetail.getIssuedDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String issuedDate= formatter.format(date);
        textIssueDate.setText(issuedDate);
        textOrderId.setText(""+orderDetail.getOrder().getOrderId());

        return layout;
    }


}
