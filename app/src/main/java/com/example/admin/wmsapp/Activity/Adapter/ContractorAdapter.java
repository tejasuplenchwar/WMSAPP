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

import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.R;

import java.util.ArrayList;

/**
 * Created by admin on 08/02/2017.
 */

public class ContractorAdapter extends ArrayAdapter<Contractor>{
    private final Context context;
    private final ArrayList<Contractor> contractors;

    public ContractorAdapter(Context context, ArrayList<Contractor> contractors) {
        super(context, 0);
        this.context=context;
        this.contractors=contractors;
  }

    @Override
    public int getCount() {
        return contractors.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contractor contractor=contractors.get(position);
        LinearLayout layout=null;

        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) layoutInflater.inflate(R.layout.contractor_list_item, null);
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

        TextView textContractorName=(TextView)layout.findViewById(R.id.textContractorName);
        TextView textContractorAddress=(TextView)layout.findViewById(R.id.textContractorAddress);
        TextView textContractorNumber=(TextView)layout.findViewById(R.id.textContractorNumber);
        TextView textContractorEmail=(TextView)layout.findViewById(R.id.textContractorEmail);

        textContractorName.setText(contractor.getCtrName());
        textContractorAddress.setText(contractor.getCtrAddress());
        textContractorNumber.setText(contractor.getCtrNum());
        textContractorEmail.setText(contractor.getCtrEmailId());
        return layout;
    }
}
