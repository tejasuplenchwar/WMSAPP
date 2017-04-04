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

import com.example.admin.wmsapp.Activity.RootObject.Invoice;
import com.example.admin.wmsapp.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 17/03/2017.
 */

public class InvoiceAdapter extends ArrayAdapter<Invoice>{
    DecimalFormat precision=new DecimalFormat("0.00");

    private final Context context;
    private final ArrayList<Invoice> invoiceList;


    public InvoiceAdapter(Context context, ArrayList<Invoice> invoiceList) {
        super(context, 0);
        this.context=context;
        this.invoiceList=invoiceList;
    }

    @Override
    public int getCount() {
        return invoiceList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Invoice invoice=invoiceList.get(position);
        LinearLayout layout=null;

        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.invo_list_item, null);
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

        TextView textSequenceId=(TextView)layout.findViewById(R.id.textSequenceId);
        TextView textContractorName=(TextView)layout.findViewById(R.id.textContractorName);

        TextView textValidDate=(TextView)layout.findViewById(R.id.textValidDate);
        TextView textInvoiceAmount=(TextView)layout.findViewById(R.id.textInvoiceAmount);

        Date invDate=new Date(invoice.getInvoiceDate());
        Date valDate=new Date(invoice.getValidDate());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String validDate=formatter.format(valDate);



        textSequenceId.setText(invoice.getSequenceId());
        textContractorName.setText(invoice.getContractor().getCtrName());

        textValidDate.setText(validDate);
        textInvoiceAmount.setText(precision.format(invoice.getInvoiceAmtTotal()));


     return layout;


    }
}
