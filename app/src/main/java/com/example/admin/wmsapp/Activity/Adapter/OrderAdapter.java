package com.example.admin.wmsapp.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.wmsapp.Activity.Model.Order;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 09/02/2017.
 */

public class OrderAdapter extends ArrayAdapter<Order> {
    private final Context context;
    private final ArrayList<Order> orders;

    LayoutInflater inflater;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0);
        this.context=context;
        this.orders=orders;
        inflater = LayoutInflater.from(context);

    }

    private class ViewHolder {
        TextView textOrderId;
        TextView textWarehouseName;
        TextView textOrderDate;
        TextView textContractorName;
        TextView textOrderStatus;
        CheckBox checkBox;

    }


    @Override
    public int getCount() {
        return orders.size();
    }



    @Nullable
    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Order order=orders.get(position);
       System.out.println(order.toString());
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.order_list_item, null);
            // Locate the TextViews in listview_item.xml
           holder.textOrderId=(TextView)view.findViewById(R.id.textOrderId);
            holder.textOrderDate=(TextView)view.findViewById(R.id.textOrderDate);
            holder.textWarehouseName=(TextView)view.findViewById(R.id.textWarehouseName);
            holder.textContractorName=(TextView)view.findViewById(R.id.textContractorName);
            holder.textOrderStatus= (TextView) view.findViewById(R.id.textOrderStatus);
            holder.checkBox=(CheckBox)view.findViewById(R.id.checkbox);

            if(MainActivity.role.equals("CTR")){
                holder.checkBox.setVisibility(View.GONE);
            }
            // Locate the ImageView in listview_item.xml

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if(order.getOrderMasterInvoiceStatus().equals("Invoiced"))
        {
            holder.checkBox.setVisibility(View.GONE);
        }
        if(order.getOrderMasterInvoiceStatus().equals("Partial Invoiced"))
        {
            holder.checkBox.setVisibility(View.VISIBLE);
        }

        if(order.getOrderMasterInvoiceStatus().equals("Delivered"))
        {
            holder.checkBox.setVisibility(View.VISIBLE);
        }

        holder.textOrderStatus.setText(order.getOrderMasterInvoiceStatus());
        holder.textOrderId.setText(order.getSequenceId());
        holder.textWarehouseName.setText(order.getWarehouse().getWarehouseName());
        holder.textContractorName.setText(order.getContractor().getCtrName());
        Date date=new Date(order.getOrderDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate= formatter.format(date);
        holder.textOrderDate.setText(strDate);

        holder.checkBox.setOnCheckedChangeListener(myCheckChangList);
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(order.isBox());
        return view;

    }

    Order getOrder(int position) {
        return ((Order) getItem(position));
    }

     public ArrayList<Order> getBox() {
        ArrayList<Order> box = new ArrayList<Order>();
        for (Order order : orders) {
            if (order.isBox())
                box.add(order);
        }
        return box;
    }
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getOrder((Integer) buttonView.getTag()).setBox(isChecked);
        }
    };

}
