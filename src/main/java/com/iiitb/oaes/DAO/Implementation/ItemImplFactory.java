package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.DAO.ItemDao;

public class ItemImplFactory {
    public static ItemDao getItemImpl(String type) {
        if (type.equals("MCQ"))
            return new MCQItemImpl();
        else if (type.equals("TrueFalse"))
            return new TrueFalseItemImpl();
        else
            return null;
            // throw new Exception("Error: Unknown Item Type!");
    }
}
