package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 02/02/2017.
 */

public class Contractor implements Serializable{
    private int  ctrId;
    private String ctrName;
    private String ctrAddress;
    private String ctrEmailId;
    private String organisationId;
    private String ctrStatus;
    private String ctrCode;

    private String ctrNum;

    public int getCtrId() {
        return ctrId;
    }

    public void setCtrId(int ctrId) {
        this.ctrId = ctrId;
    }

    public String getCtrName() {
        return ctrName;
    }

    public void setCtrName(String ctrName) {
        this.ctrName = ctrName;
    }

    public String getCtrAddress() {
        return ctrAddress;
    }

    public void setCtrAddress(String ctrAddress) {
        this.ctrAddress = ctrAddress;
    }

    public String getCtrNum() {
        return ctrNum;
    }

    public void setCtrNum(String ctrNum) {
        this.ctrNum = ctrNum;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public String getCtrEmailId() {
        return ctrEmailId;
    }

    public void setCtrEmailId(String ctrEmailId) {
        this.ctrEmailId = ctrEmailId;
    }

    public String getCtrStatus() {
        return ctrStatus;
    }

    public void setCtrStatus(String ctrStatus) {
        this.ctrStatus = ctrStatus;
    }

    public String getCtrCode() {
        return ctrCode;
    }

    public void setCtrCode(String ctrCode) {
        this.ctrCode = ctrCode;
    }

    @Override
    public String toString() {
        return ctrName;

    }
}
