package com.example.admin.wmsapp.Activity.Model;

/**
 * Created by admin on 28/03/2017.
 */

public class CategoryKey {
    private int categoryId;
    private String categoryName;
    private String categoryDesc;
    private String categoryStatus;
    private String organisationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryKey)) return false;

        CategoryKey that = (CategoryKey) o;

        if (categoryId != that.categoryId) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null)
            return false;
        if (categoryDesc != null ? !categoryDesc.equals(that.categoryDesc) : that.categoryDesc != null)
            return false;
        if (categoryStatus != null ? !categoryStatus.equals(that.categoryStatus) : that.categoryStatus != null)
            return false;
        return organisationId != null ? organisationId.equals(that.organisationId) : that.organisationId == null;

    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (categoryDesc != null ? categoryDesc.hashCode() : 0);
        result = 31 * result + (categoryStatus != null ? categoryStatus.hashCode() : 0);
        result = 31 * result + (organisationId != null ? organisationId.hashCode() : 0);
        return result;
    }
}
