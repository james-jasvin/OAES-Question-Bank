package com.iiitb.oaes.Driver;

import com.iiitb.oaes.Bean.*;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.DAO.CourseDao;
import com.iiitb.oaes.DAO.Implementation.*;
import com.iiitb.oaes.DAO.ItemDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void clearDatabase() {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Course");
            query.executeUpdate();

            query = session.createQuery("delete from Item");
            query.executeUpdate();

            query = session.createQuery("delete from Author");
            query.executeUpdate();
            transaction.commit();
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }

    public static void initializeAuthorDatabase() {
        AuthorImpl authorsDao = new AuthorImpl();

        // Adding authors
        Author author = new Author("John White","john_white","John@123");
        authorsDao.registerAuthor(author);

        author = new Author("Alice Bob","alice_bob","Alice@123");
        authorsDao.registerAuthor(author);

        // Displaying authors
        System.out.println("-------------------------------------------\nDisplaying Author\n-------------------------------------------");
        List<Author> authorsList = authorsDao.getAuthors();

        for(Author authors:authorsList)
            System.out.println(authors);
    }

    public static void initializeCourseDatabase() {
        CourseImpl courseDao = new CourseImpl();

        // Adding courses
        Course course = new Course("Software Architecture","SA");
        courseDao.createCourse(course);

        course = new Course("Software Testing", "ST");
        courseDao.createCourse(course);

        // Displaying courses
        System.out.println("-------------------------------------------\nDisplaying Courses\n-------------------------------------------");
        List<Course> courseList = courseDao.getCourses();
        for(Course curCourse: courseList)
            System.out.println(curCourse);
    }

    public static void initializeItemDatabase(){
        // Adding Items
        System.out.println("-------------------------------------------\nUse Case 1: Adding Items\n-------------------------------------------");
        ItemDao itemDao = new ItemImpl();

        // Adding MCQ Item 1
        Item item = new MCQItem("In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
            "Factory Pattern",
            "Abstract Factory Pattern",
            "Singleton Pattern",
            "Transfer Object Pattern",
            2
        );

        AuthorDao authorImpl = new AuthorImpl();

        Author john = new Author("John White", "john_white", "John@123");
        Author alice = new Author("Alice Bob", "alice_bob", "Alice@123");

        john = authorImpl.loginAuthor(john);
        alice = authorImpl.loginAuthor(alice);
        itemDao.createItem(item, john, 1);

        // Adding MCQ Item 2
        item = new MCQItem("Which of the below is not a valid classification of design pattern",
                "Creational patterns",
                "Structural patterns",
                "Behavioural patterns",
                "J2EE patterns",
                4
        );
        itemDao.createItem(item, alice, 1);

        // Adding True False Item 1
        item = new TrueFalseItem("Is the Earth Flat?",
                true
        );
        itemDao.createItem(item, alice, 1);

        // Displaying MCQ items
        List<Item> savedItems = itemDao.getItems(alice.getAuthorId());
        for (Item i: savedItems)
            System.out.println(i);

        // Display True False items
        savedItems = itemDao.getItems(alice.getAuthorId());
        for (Item i: savedItems)
            System.out.println(i);

        // Updating MCQ Item with 4 parameters with correct login credentials, update successful
        MCQItem mcqItem = new MCQItem(2);
        mcqItem.setQuestion("Which of the below is a valid classification of design pattern");
        mcqItem.setOption2("Model-View-Controller");
        mcqItem.setOption3("Abstract Interface");
        mcqItem.setAnswer(1);

        System.out.println(itemDao.updateItem(mcqItem, alice.getAuthorId())? "Update successful": "Update failed");

        // Updating MCQ Item with 3 parameters and invalid login credentials, update fails
        mcqItem = new MCQItem(1);
        mcqItem.setOption4("Observer Pattern");
        mcqItem.setOption2("Abstract Factory Pattern");
        mcqItem.setAnswer(2);
        System.out.println(itemDao.updateItem(mcqItem, alice.getAuthorId())? "Update successful": "Update failed");

        // Updating True False Item
        TrueFalseItem tfItem = new TrueFalseItem(3);
        tfItem.setAnswer(false);

        System.out.println(itemDao.updateItem(tfItem, alice.getAuthorId())? "Update successful": "Update failed");
    }

    public static void displayCourses(CourseDao courseDao) {
        System.out.println("-------------------------------------------\nDisplaying Courses\n-------------------------------------------");
        List<Course> savedCourses = courseDao.getCourses();

        for (Course c: savedCourses)
            System.out.println(c);

    }

    public static void displayItems(ItemDao itemImpl, Author author) {
        System.out.println("-------------------------------------------\nDisplaying Items\n-------------------------------------------");
        List<Item> savedItems = itemImpl.getItems(author.getAuthorId());
        for (Item i: savedItems)
            System.out.println(i);
    }

    public static Integer getCourseIdInput(Scanner sc) {
        System.out.println("Enter Course Id for Item");
        return Integer.parseInt(sc.nextLine());
    }

    public static MCQItem getMCQItemInput(Scanner sc) {
        System.out.println("Enter Question :-");
        String ques = sc.nextLine();

        System.out.println("Enter Option 1 :-");
        String opt1 = sc.nextLine();

        System.out.println("Enter Option 2 :-");
        String opt2 = sc.nextLine();

        System.out.println("Enter Option 3 :-");
        String opt3 = sc.nextLine();

        System.out.println("Enter Option 4 :-");
        String opt4 = sc.nextLine();

        System.out.println("Enter Answer :-");
        Integer ans = Integer.parseInt(sc.nextLine());

        return new MCQItem(ques, opt1, opt2, opt3, opt4, ans);
    }

    public static TrueFalseItem getTrueFalseItemInput(Scanner sc) {
        System.out.println("Enter Question :-");
        String ques = sc.nextLine();
        System.out.println("Enter Answer :- \n 1.True\n 2.False");
        Boolean ans = Integer.parseInt(sc.nextLine()) != 2;

        return new TrueFalseItem(ques, ans);
    }

    public static void updateMCQItem(Scanner sc, ItemDao itemImpl, Author loggedInAuthor) {
        System.out.println("-------------------------------------------\nUpdating MCQ Item\n-------------------------------------------");

        System.out.println("Enter -1 to not update that parameter");
        System.out.println("Enter Question ID to update");

        Integer qid = Integer.parseInt(sc.nextLine());
        MCQItem item = new MCQItem(qid);

        System.out.println("Enter New Question");
        String ques = sc.nextLine();
        if (!ques.equals("-1"))
            item.setQuestion(ques);

        System.out.println("Enter Option 1");
        String opt1 = sc.nextLine();
        if (!opt1.equals("-1"))
            item.setOption1(opt1);

        System.out.println("Enter Option 2");
        String opt2 = sc.nextLine();
        if (!opt2.equals("-1"))
            item.setOption2(opt2);

        System.out.println("Enter Option 3");
        String opt3 = sc.nextLine();
        if (!opt3.equals("-1"))
            item.setOption3(opt3);

        System.out.println("Enter Option 4");
        String opt4 = sc.nextLine();
        if (!opt4.equals("-1"))
            item.setOption4(opt4);

        System.out.println("Enter New Answer");
        Integer ans = Integer.parseInt(sc.nextLine());
        if (!ans.equals(-1))
            item.setAnswer(ans);

        System.out.println(itemImpl.updateItem(item, loggedInAuthor.getAuthorId())? "Update Successful": "Update failed");
        System.out.println("Item Updated Successfully");
    }

    public static void updateTrueFalseItem(Scanner sc, ItemDao itemImpl, Author loggedInAuthor) {
        System.out.println("-------------------------------------------\nUpdating True False Item\n-------------------------------------------");

        System.out.println("Enter -1 to not update that parameter");
        System.out.println("Enter Question ID to update");

        Integer qid = Integer.parseInt(sc.nextLine());
        TrueFalseItem item = new TrueFalseItem(qid);

        System.out.println("Enter New Question");
        String ques = sc.nextLine();
        if (!ques.equals("-1"))
            item.setQuestion(ques);

        System.out.println("Enter Answer :- \n 1.True\n 2.False");
        Integer ans = Integer.parseInt(sc.nextLine());

        if (!ans.equals(-1))
            item.setAnswer(ans!=2);

        System.out.println(itemImpl.updateItem(item, loggedInAuthor.getAuthorId())? "Update Successful": "Update failed");
        System.out.println("Item Updated Successfully");
    }

    public static void main(String[] args) {
        clearDatabase();
        initializeAuthorDatabase();
        initializeCourseDatabase();
        initializeItemDatabase();

        // Login Author
        // Save logged in author object as Driver class member and use it to pass in authorization parameters for later
        // operations

        // Add Item
        // Ask for type of Question to add => MCQ or True False
        // Create corresponding Impl using Factory
        // Ask for corresponding parameters
        // Show list of courses and select course using id
        // Execute existing method using Impl

        // Get Items
        // Ask for type of Question to show => MCQ or True False
        // Create corresponding Impl using Factory
        // Show list of items using Impl

        // Update Item
        // Ask for type of Question to show => MCQ or True False
        // Create corresponding Impl using Factory
        // Show Current Items
        // Select from list using id
        // Enter updated question
        // Updated options
        // Updated correct answer

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        CourseDao courseDao = new CourseImpl();

        Author loggedInAuthor;

        while (true) {
            System.out.println("Enter Login-ID and Password :-");
            String loginId = sc.nextLine();
            String password = sc.nextLine();
            Author tempAuthor = new Author("Dummy Name", loginId, password);

            // Initiating Chain of Responsibility
            InitiateLogin h1 = new InitiateLogin();
            Sanitize h2 = new Sanitize();
            Login h3 = new Login();

            h1.setNext(h2);
            h2.setNext(h3);

            loggedInAuthor = h1.handle(tempAuthor);

            // Login Author
            System.out.println("-------------------------------------------\nLogin\n-------------------------------------------");
            if (loggedInAuthor != null) {
                System.out.println("Login Successful");
                ItemDao itemDao = new ItemImpl();
                displayItems(itemDao, loggedInAuthor);
                break;
            }
            else
                System.out.println("Login-Id or Password is Wrong");
        }

        while(choice != 4) {
//             Save logged in author object as Driver class member and use it to pass in authorization parameters for later operations

            System.out.println("Select operation from below options\n 1. Add Items\n 2. Show Items\n 3. Update Items\n 4. Exit");
            choice = Integer.parseInt(sc.nextLine());

            ItemDao itemDao = new ItemImpl();
            Integer ques_choice = 1, qtype;

            switch (choice){
                case 1:
                    // Ask for Item Type => Create correct ItemImpl
                    // Add Item
                    // Ask for parameters
                    while(ques_choice == 1) {
                        System.out.println("-------------------------------------------\nAdding Items\n-------------------------------------------");

                        displayCourses(courseDao);
                        Integer cid = getCourseIdInput(sc);

                        //Get question type
                        System.out.println("Select Question Type :\n 1.MCQ\n 2. True or False");
                        qtype = Integer.parseInt(sc.nextLine());
                        Item item;

                        if (qtype == 1)
                            item = getMCQItemInput(sc);
                        else
                            item = getTrueFalseItemInput(sc);

                        boolean isItemAdded = itemDao.createItem(item, loggedInAuthor, cid);
                        if (isItemAdded)
                            System.out.println("Item Added Successfully\n");
                        else
                            System.out.println("Item is not added!\n");

                        System.out.println("Want to add questions?\n 1. YES\n 2. NO");
                        ques_choice = Integer.parseInt(sc.nextLine());
                    }
                    break;

                case 2:
                    // Get Items
                    // Show list of items
                    displayItems(itemDao, loggedInAuthor);
                    break;

                case 3:
                    // Enter updated question
                    // Updated options
                    // Updated correct answer

                    // Show Current Items

                    //Get question type
                    System.out.println("Select Question Type :\n 1.MCQ\n 2. True or False");
                    qtype = Integer.parseInt(sc.nextLine());

                    displayItems(itemDao, loggedInAuthor);

                    if (qtype == 1)
                        updateMCQItem(sc, itemDao, loggedInAuthor);
                    else
                        updateTrueFalseItem(sc, itemDao, loggedInAuthor);

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
