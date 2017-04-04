package com.example.admin.wmsapp.Activity.Model;

import java.io.Serializable;

/**
 * Created by admin on 27/03/2017.
 */

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
    private String categoryDesc;
    private String categoryStatus;
    private String organisationId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (categoryId != category.categoryId) return false;
        if (!categoryName.equals(category.categoryName)) return false;
        if (!categoryDesc.equals(category.categoryDesc)) return false;
        if (!categoryStatus.equals(category.categoryStatus)) return false;
        return organisationId.equals(category.organisationId);

    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + categoryName.hashCode();
        result = 31 * result + categoryDesc.hashCode();
        result = 31 * result + categoryStatus.hashCode();
        result = 31 * result + organisationId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return  categoryName;
    }
}
