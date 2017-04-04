package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 04/04/2017.
 */

public class Menu implements Serializable {

    private int menu_id;
    private String menu_name;
    private String menu_icon;

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }
}
