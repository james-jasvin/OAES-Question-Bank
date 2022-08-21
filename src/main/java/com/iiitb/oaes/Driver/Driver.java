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
        System.out.println("-------------------------------------------\nAdding Authors\n-------------------------------------------");
        Authors author = new Authors("John White","john_white","John@123");
        authorsDao.registerAuthor(author);
        System.out.println("Added Author 1\n");

        author = new Authors("Alice Bob","alice_bob","Alice@123");
        authorsDao.registerAuthor(author);
        System.out.println("Added Author 2");

        // Displaying authors
        System.out.println("-------------------------------------------\nDisplaying Authors\n-------------------------------------------");
        List<Authors> authorsList = authorsDao.getAuthors();
        for(Authors authors:authorsList){
            System.out.println(authors);
        }

        // Login author
        // Wrong password
        System.out.println("-------------------------------------------\nLogin\n-------------------------------------------");
        System.out.println("Testing Login with Wrong Password");
        System.out.println(authorsDao.loginAuthor("john_white","Alice@123") == null? "Login failed": "Login successful");

        // Correct password
        System.out.println("\nTesting Login with Correct Password");
        System.out.println(authorsDao.loginAuthor("alice_bob","Alice@123") == null? "Login failed": "Login successful");

        // Adding Items
        System.out.println("-------------------------------------------\nUse Case 1: Adding Items\n-------------------------------------------");
        ItemsImpl itemsDao = new ItemsImpl();

        Items item = new Items("In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
                "Factory Pattern",
                "Abstract Factory Pattern",
                "Singleton Pattern",
                "Transfer Object Pattern",
                2
        );
        itemsDao.createItem(item);
        System.out.println("Added Item 1\n");

        item = new Items("Which of the below is not a valid classification of design pattern",
                "Creational patterns",
                "Structural patterns",
                "Behavioural patterns",
                "J2EE patterns",
                4
        );
        itemsDao.createItem(item);
        System.out.println("Added Item 2");

        // Displaying items
        System.out.println("-------------------------------------------\nUse Case 2: Displaying Items\n-------------------------------------------");
        List<Items> savedItems = itemsDao.getItems();

        for (Items i: savedItems)
            System.out.println(i);

        // Updating Item with 2 parameters
        System.out.println("-------------------------------------------\nUse Case 3: Updating Item\n-------------------------------------------");
        item = new Items(2);
        item.setQuestion("Which of the below is a valid classification of design pattern");
        item.setOption2("Model-View-Controller");
        item.setOption3("Abstract Interface");
        item.setAnswer(1);
        itemsDao.updateItem(item);
        System.out.println("Finished Updating Item 1\n");

        // Updating Item with 3 parameters
        item = new Items(2);
        item.setOption2("Observer Pattern");
        item.setOption4("Abstract Factory Pattern");
        item.setAnswer(4);
        itemsDao.updateItem(item);
        System.out.println("Finished Updating Item 2\n");
    }

    public static void main(String[] args) {
        clearDatabase();
        initializeDatabase();
    }
}
