package com.iiitb.oaes.utils;

import com.iiitb.oaes.Bean.Item;

import java.util.List;

// Utility class to store Cache Items of ProxyItemImpl class
public class CachePair {
    List<Item> items;
    Boolean needReset;

    public CachePair() {
    }

    public CachePair(List<Item> items, Boolean needReset) {
        this.items = items;
        this.needReset = needReset;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Boolean getNeedReset() {
        return needReset;
    }

    public void setNeedReset(Boolean needReset) {
        this.needReset = needReset;
    }
}
