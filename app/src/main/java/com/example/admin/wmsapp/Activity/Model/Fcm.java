package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 07/03/2017.
 */

public class Fcm {
    public int serial_id ;
    public String token_id;
    public String imei ;
    public String contractor_email;

    public int getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(int serial_id) {
        this.serial_id = serial_id;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getContractor_email() {
        return contractor_email;
    }

    public void setContractor_email(String contractor_email) {
        this.contractor_email = contractor_email;
    }
}
