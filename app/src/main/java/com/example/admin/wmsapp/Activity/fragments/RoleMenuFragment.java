package com.example.admin.wmsapp.Activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.wmsapp.Activity.tabs.Pager;
import com.example.admin.wmsapp.Activity.tabs.PagerRole;
import com.example.admin.wmsapp.R;

/**
 * Created by admin on 04/04/2017.
 */

public class RoleMenuFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_rolemenu, container, false);
       tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
       tabLayout.addTab(tabLayout.newTab().setText("Inventory Details"));
       tabLayout.addTab(tabLayout.newTab().setText("Your Tab Title"));
       tabLayout.addTab(tabLayout.newTab().setText("Your Tab Title"));
       tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       viewPager = (ViewPager)v.findViewById(R.id.pager);

       //Creating our pager adapter
       PagerRole adapter = new PagerRole(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

       //Adding adapter to pager
       viewPager.setAdapter(adapter);
       tabLayout.setupWithViewPager(viewPager);
       //Adding onTabSelectedListener to swipe views
       tabLayout.setOnTabSelectedListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Rolemenu Mapping");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
