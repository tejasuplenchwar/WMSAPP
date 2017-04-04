package com.example.admin.wmsapp.Activity.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by admin on 04/04/2017.
 */

public class PagerRole extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerRole(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Role role = new Role();
                return role;
            case 1:
                Menu menu = new Menu();
                return menu;
            case 2:
                RoleMenu roleMenu = new RoleMenu();
                return roleMenu;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String name=null;
        if(position==0)
        {
            name="Role";
        }
        if(position==1)
        {
            name="Menu";
        }
        if(position==2)
        {
            name="Role-Menu";
        }
        return name;

    }
}
