package com.iiitb.oaes.Driver;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.Implementation.AuthorsImpl;
import com.iiitb.oaes.DAO.Implementation.ItemsImpl;

import java.util.List;


public class Driver {
    public static void main(String[] args) {
        AuthorsImpl authorsDao = new AuthorsImpl();
//        Authors author = new Authors("JJ","JJ123","JJ@123");
//        authorsDao.registerAuthor(author);

        Authors author = new Authors("GT","GT123","GT@123");
        authorsDao.registerAuthor(author);
        List<Authors> authorsList= authorsDao.getAuthors();
        for(Authors authors:authorsList){
            System.out.println(authors);
        }

        System.out.println(authorsDao.loginAuthor("GT123","GTU"));
        System.out.println(authorsDao.loginAuthor("GT123","GT@123"));

        
        ItemsImpl itemsDao = new ItemsImpl();
//        Items item = new Items("Question2","option1","option2","option3","option4", 2);
//        itemsDao.createItem(item);

//        List<Items> savedItems = itemsDao.getItems();
//
//        for (Items item: savedItems) {
//            System.out.println(item);
//        }
//        Items item = new Items(1);
//        item.setQuestion("Quest42");
//        item.setOption4("Opt4");
//        itemsDao.updateItem(item);
    }
}
