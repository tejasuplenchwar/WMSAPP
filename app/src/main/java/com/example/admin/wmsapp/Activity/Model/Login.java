package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 22/03/2017.
 */

public class Login implements Serializable {
    private String email;
    private String password;
    private String app;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
