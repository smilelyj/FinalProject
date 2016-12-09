package org.swe.android.datasource;

import java.util.List;

/**
 * Created by YongjiLi on 2/1/16.
 */

public class TemplateList{//to restore the class in the adapter
    private List<TemplateListItem> items;

    public List<TemplateListItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateListItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "TemplateList{" +
                ", items=" + items +
                '}';
    }
}