package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Items;

import java.util.List;

public interface ItemsDao {
    boolean createItem(Items item);
    List<Items> getItems();
    boolean updateItem(Items item);
}
