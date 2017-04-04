package com.example.admin.wmsapp.Activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.wmsapp.Activity.activity.LoginActivity;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;

/**
 * Created by admin on 24/03/2017.
 */

public class MyProfileFragment extends Fragment {
   TextView textName,textMail,textOrganisation,textRole;
    Button buttonLogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_myprofile, container, false);
        textName= (TextView) view.findViewById(R.id.textName);
        textMail= (TextView) view.findViewById(R.id.textMail);
        textOrganisation= (TextView) view.findViewById(R.id.textOrganisation);
        textRole= (TextView) view.findViewById(R.id.textRole);

        textName.setText(MainActivity.name);
        textMail.setText(MainActivity.email);
        textOrganisation.setText(MainActivity._orgName);
        if(MainActivity.role.equals("CTR")) {
            textRole.setText("Contractor");
        }
        if(MainActivity.role.equals("ADM")) {
            textRole.setText("Administrator");
        }
        if(MainActivity.role.equals("WHM")) {
            textRole.setText("Warehouse Manager");
        }

        if(MainActivity.role.equals("generic")) {
            textRole.setText("GENERIC");
        }
        buttonLogout= (Button) view.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Profile");
    }

}
