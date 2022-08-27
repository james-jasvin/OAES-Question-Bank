package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Items;

import java.util.List;

public interface ItemsDao {
    boolean createItem(Items item, String loginId, String password, Integer courseId);
    List<Items> getItems(String loginId, String password);
    boolean updateItem(Items item, String loginId, String password);
}
