package com.iiitb.oaes.Driver;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.Implementation.AuthorsImpl;
import com.iiitb.oaes.DAO.Implementation.ItemsImpl;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


public class Driver {
    public static void clearDatabase() {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Items");
            query.executeUpdate();

            query = session.createQuery("delete from Authors");
            query.executeUpdate();
            transaction.commit();
        }  catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }
    public static void initializeDatabase() {

        AuthorsImpl authorsDao = new AuthorsImpl();

        // Adding authors
        Authors author = new Authors("JJ","JJ123","JJ@123");
        authorsDao.registerAuthor(author);

        author = new Authors("GT","GT123","GT@123");
        authorsDao.registerAuthor(author);

        // Displaying authors
        List<Authors> authorsList = authorsDao.getAuthors();
        for(Authors authors:authorsList){
            System.out.println(authors);
        }

        // Login author
        // Wrong password
        System.out.println(authorsDao.loginAuthor("GT123","GTU"));
        // Correct password
        System.out.println(authorsDao.loginAuthor("GT123","GT@123"));

        // Adding Items
        ItemsImpl itemsDao = new ItemsImpl();
        Items item = new Items("Question1","option1","option2","option3","option4", 3);
        itemsDao.createItem(item);

        item = new Items("Question2","option1","option2","option3","option4", 2);
        itemsDao.createItem(item);

        // Displaying items
        List<Items> savedItems = itemsDao.getItems();

        for (Items i: savedItems)
            System.out.println(i);

        // Updating Item with 2 parameters
        item = new Items(1);
        item.setQuestion("Quest42");
        item.setOption4("Opt4");
        itemsDao.updateItem(item);

        // Updating Item with 3 parameters
        item = new Items(2);
        item.setQuestion("Quest20");
        item.setOption1("OptA");
        item.setOption2("OptB");
        itemsDao.updateItem(item);

    }

    public static void main(String[] args) {
        clearDatabase();
        initializeDatabase();
    }
}
