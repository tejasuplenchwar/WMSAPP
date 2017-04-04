package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 22/03/2017.
 */

public class LoginResponse {

    private String token;
    private String role;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
