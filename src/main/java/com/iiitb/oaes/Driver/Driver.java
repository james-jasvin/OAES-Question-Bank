package com.iiitb.oaes.Driver;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.Bean.Course;
import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.CourseDao;
import com.iiitb.oaes.DAO.Implementation.*;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void clearDatabase() {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Course");
            query.executeUpdate();

            query = session.createQuery("delete from Items");
            query.executeUpdate();

            query = session.createQuery("delete from Authors");
            query.executeUpdate();
            transaction.commit();
        }  catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }

    public static void initializeAuthorDatabase() {
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
//        System.out.println("-------------------------------------------\nLogin\n-------------------------------------------");
//        System.out.println("Testing Login with Wrong Password");
//        System.out.println(authorsDao.loginAuthor("john_white","Alice@123") == null? "Login failed": "Login successful");
//
//        // Correct password
//        System.out.println("\nTesting Login with Correct Password");
//        System.out.println(authorsDao.loginAuthor("alice_bob","Alice@123") == null? "Login failed": "Login successful");
    }

    public static void initializeCourseDatabase() {
        CourseImpl courseDao = new CourseImpl();

        // Adding course
        System.out.println("-------------------------------------------\nAdding Courses\n-------------------------------------------");
        Course course = new Course("Software Architecture","SA");
        courseDao.createCourse(course);
        System.out.println("Added Course 1\n");

        course = new Course("Software Testing", "ST");
        courseDao.createCourse(course);
        System.out.println("Added Course 2");

        // Displaying courses
        System.out.println("-------------------------------------------\nDisplaying Courses\n-------------------------------------------");
        List<Course> courseList = courseDao.getCourses();
        for(Course curCourse: courseList)
            System.out.println(curCourse);
    }

    public static void initializeItemDatabase(){
        // Adding Items
        System.out.println("-------------------------------------------\nUse Case 1: Adding Items\n-------------------------------------------");
        ProxyItemsImpl itemsDao = new ProxyItemsImpl();

        Items item = new Items("In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
            "Factory Pattern",
            "Abstract Factory Pattern",
            "Singleton Pattern",
            "Transfer Object Pattern",
            2
        );

        String loginId = "john_white", password = "John@123";
        itemsDao.createItem(item, loginId, password, 1);
        System.out.println("Added Item 1\n");

        item = new Items("Which of the below is not a valid classification of design pattern",
                "Creational patterns",
                "Structural patterns",
                "Behavioural patterns",
                "J2EE patterns",
                4
        );

        loginId = "alice_bob"; password = "Alice@123";
        itemsDao.createItem(item, loginId, password, 1);
        System.out.println("Added Item 2");

        // Displaying items
        System.out.println("-------------------------------------------\nUse Case 2: Displaying Items\n-------------------------------------------");
        List<Items> savedItems = itemsDao.getItems(loginId, password);

        for (Items i: savedItems)
            System.out.println(i);

        // Updating Item with 2 parameters with invalid login credentials, update fails
        System.out.println("-------------------------------------------\nUse Case 3: Updating Item\n-------------------------------------------");
        item = new Items(2);
        item.setQuestion("Which of the below is a valid classification of design pattern");
        item.setOption2("Model-View-Controller");
        item.setOption3("Abstract Interface");
        item.setAnswer(1);
        itemsDao.updateItem(item, loginId, password);
        System.out.println(itemsDao.updateItem(item, loginId, password)? "Update failed": "Update successful");
        System.out.println("Finished Updating Item 1\n");

        // Updating Item with 3 parameters and correct login credentials, update successful
        item = new Items(1);
        item.setOption4("Observer Pattern");
        item.setOption2("Abstract Factory Pattern");
        item.setAnswer(2);
        System.out.println(itemsDao.updateItem(item, loginId, password)? "Update failed": "Update successful");
        System.out.println("Finished Updating Item 2\n");
    }

    public static void main(String[] args) {
        clearDatabase();
        initializeAuthorDatabase();
        initializeCourseDatabase();
        initializeItemDatabase();

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        AuthorsImpl authorsDao = new AuthorsImpl();
        CourseDao courseDao = new CourseImpl();

        String loginId,password;

        while (true) {
            System.out.println("Enter Login-ID and Password :-");
            loginId = sc.nextLine();
            password = sc.nextLine();

            InitiateLogin h1 = new InitiateLogin();
            Sanitize h2 = new Sanitize();
            Login h3 = new Login();

            h1.setNext(h2);
            h2.setNext(h3);

            Authors author = h1.handle(loginId,password);

//            Authors author = authorsDao.loginAuthor(loginId,password);

            // Login Author
            System.out.println("-------------------------------------------\nLogin\n-------------------------------------------");
            if(author != null){
                System.out.println("Login Successful");
                break;
            }else{
                System.out.println("Login-Id or Password is Wrong");
            }
        }

        while(choice != 4){
            // Save logged in author object as Driver class member and use it to pass in authorization parameters for later operations

            System.out.println("Select operation from below options\n 1. Add Items\n 2. Show Items\n 3. Update Items\n 4. Exit");
            choice = Integer.parseInt(sc.nextLine());

            ProxyItemsImpl itemsDao = new ProxyItemsImpl();
            Integer ques_choice = 1;
            String ques,opt1,opt2,opt3,opt4;
            Integer ans;

            switch (choice){
                case 1:

                    // Add Item
                    // Ask for parameters
                    while(ques_choice == 1) {
                        System.out.println("-------------------------------------------\nAdding Items\n-------------------------------------------");
                        System.out.println("-------------------------------------------\nDisplaying Courses\n-------------------------------------------");
                        List<Course> savedCourses = courseDao.getCourses();

                        for (Course c: savedCourses)
                            System.out.println(c);

                        System.out.println("Enter Course Id for Item");
                        Integer cid = Integer.parseInt(sc.nextLine());

                        System.out.println("Enter Question :-");
                        ques = sc.nextLine();

                        System.out.println("Enter Option 1 :-");
                        opt1 = sc.nextLine();

                        System.out.println("Enter Option 2 :-");
                        opt2 = sc.nextLine();

                        System.out.println("Enter Option 3 :-");
                        opt3 = sc.nextLine();

                        System.out.println("Enter Option 4 :-");
                        opt4 = sc.nextLine();

                        System.out.println("Enter Answer :-");
                        ans = Integer.parseInt(sc.nextLine());

                        Items item = new Items(ques, opt1, opt2, opt3, opt4, ans);
                        boolean isItemAdded = itemsDao.createItem(item, loginId, password, cid);
                        if (isItemAdded)
                            System.out.println("Item Added Successfully\n");
                        else
                            System.out.println("Item is not added !\n");

                        System.out.println("Want to add questions ?\n 1. YES\n 2. NO");
                        ques_choice = Integer.parseInt(sc.nextLine());
//                        sc.nextLine();
                    }
                    break;

                case 2:
                    // Get Items
                    // Show list of items

                    System.out.println("-------------------------------------------\nDisplaying Items\n-------------------------------------------");
                    List<Items> savedItems = itemsDao.getItems(loginId, password);
                    for (Items i: savedItems)
                        System.out.println(i);
                    break;

                case 3:
                    // Enter updated question
                    // Updated options
                    // Updated correct answer

                    // Show Current Items
                    System.out.println("-------------------------------------------\nDisplaying Items\n-------------------------------------------");
                    savedItems = itemsDao.getItems(loginId, password);
                    for (Items i: savedItems)
                        System.out.println(i);

                    System.out.println("-------------------------------------------\nUpdating Item\n-------------------------------------------");
                    System.out.println("Enter -1 to not update that parameter");
                    System.out.println("Enter Question id to update");
                    Integer qid = Integer.parseInt(sc.nextLine());
                    Items item = new Items(qid);

                    System.out.println("Enter New Question");
                    ques = sc.nextLine();
                    if(!ques.equals("-1"))
                        item.setQuestion(ques);

                    System.out.println("Enter Option 1");
                    opt1 = sc.nextLine();
                    if(!opt1.equals("-1"))
                        item.setOption1(opt1);

                    System.out.println("Enter Option 2");
                    opt2 = sc.nextLine();
                    if(!opt2.equals("-1"))
                        item.setOption2(opt2);

                    System.out.println("Enter Option 3");
                    opt3 = sc.nextLine();
                    if(!opt3.equals("-1"))
                        item.setOption3(opt3);

                    System.out.println("Enter Option 4");
                    opt4 = sc.nextLine();
                    if(!opt4.equals("-1"))
                        item.setOption4(opt4);

                    System.out.println("Enter New Answer");
                    ans = Integer.parseInt(sc.nextLine());
                    if(!ans.equals(-1))
                        item.setAnswer(ans);
//                    sc.nextLine();

                    itemsDao.updateItem(item, loginId, password);
                    System.out.println(itemsDao.updateItem(item, loginId, password)? "Update Successful": "Update failed");
                    System.out.println("Item Updated Successfully");
                    break;
                case 4:
                    System.out.println("\n\n-------------------------------------------THANK YOU-------------------------------------------\n\n");
                    break;
                default:
                    System.out.println("Enter Valid Choice");
            }
        }
    }
}
