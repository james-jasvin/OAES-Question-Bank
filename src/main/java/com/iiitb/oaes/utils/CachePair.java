package com.iiitb.oaes.utils;

import com.iiitb.oaes.Bean.Items;

import java.util.List;

public class CachePair {
    List<Items> items;
    Boolean needReset;

    public CachePair(){}

    public CachePair(List<Items> items, Boolean needReset) {
        this.items = items;
        this.needReset = needReset;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Boolean getNeedReset() {
        return needReset;
    }

    public void setNeedReset(Boolean needReset) {
        this.needReset = needReset;
    }
}
