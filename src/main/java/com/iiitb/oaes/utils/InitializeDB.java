package com.iiitb.oaes.utils;

import com.iiitb.oaes.Bean.*;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.DAO.CourseDao;
import com.iiitb.oaes.DAO.Implementation.AuthorImpl;
import com.iiitb.oaes.DAO.Implementation.CourseImpl;
import com.iiitb.oaes.DAO.Implementation.ItemImpl;
import com.iiitb.oaes.DAO.ItemDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
    For the clearDatabase() method to work, the "hibernate.hbm2ddl.auto" property in hibernate.cfg.xml has to be set to
    "create-drop", otherwise it will show dependency error while deleting Item table and you'll end up with empty database
 */
public class InitializeDB {
    public static void clearDatabase() {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Item");
            query.executeUpdate();

            query = session.createQuery("delete from Course");
            query.executeUpdate();

            query = session.createQuery("delete from Author");
            query.executeUpdate();
            transaction.commit();
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        clearDatabase();

        List<List<String>> authors = Arrays.asList(
                Arrays.asList("Jasvin James","jasvin_james","Jasvin@123"),
                Arrays.asList("Gaurav Tirodkar","gaurav_tirodkar","Gaurav@123"),
                Arrays.asList("Niraj Gujarathi","niraj_gujarathi","Niraj@123"),
                Arrays.asList("Arjun Dutta","arjun_dutta","Arjun@123")
        );

        List<List<String>> courses = Arrays.asList(
                Arrays.asList("Software Architecture","SA"),
                Arrays.asList("Software Testing","ST"),
                Arrays.asList("Network Science for the Web","NSW"),
                Arrays.asList("Advanced Cyber Security","ACY")
        );

        // Input format: (Question, Options, Answer, authorListIndex, courseId (from DB, starts at id = 1))
        List<List<String>> mcqItems = Arrays.asList(
                Arrays.asList(
                        "In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
                        "Factory Pattern",
                        "Abstract Factory Pattern",
                        "Singleton Pattern",
                        "Transfer Object Pattern",
                        "2",
                        "0",
                        "1"
                ),
                Arrays.asList(
                        "Which of the below is not a valid classification of design pattern?",
                        "Creational Patterns",
                        "Structural Patterns",
                        "Behavioural Patterns",
                        "J2EE Patterns",
                        "4",
                        "1",
                        "2"
                )
        );

        // Input format: (Question, Answer, authorListIndex, courseId (from DB, starts at id = 1))
        List<List<String>> trueFalseItems = Arrays.asList(
                Arrays.asList(
                        "Is the Earth Flat?",
                        "false",
                        "2",
                        "3"
                ),
                Arrays.asList(
                        "Can design patterns be used in Architecture as well?",
                        "true",
                        "3",
                        "4"
                )
        );

        AuthorDao authorDao = new AuthorImpl();
        ItemDao itemDao = new ItemImpl();
        CourseDao courseDao = new CourseImpl();

        List<Course> courseList = new ArrayList<>();
        for (List<String> course: courses) {
            Course courseObj = new Course(course.get(0), course.get(1));
            courseList.add(courseDao.createCourse(courseObj));
        }

        List<Author> authorList = new ArrayList<>();
        for (List<String> author: authors) {
            Author authorObj = new Author(author.get(0), author.get(1), author.get(2));
            authorList.add(authorDao.registerAuthor(authorObj));
        }

        for (List<String> mcqItem: mcqItems) {
            MCQItem mcqItemObj = new MCQItem(mcqItem.get(0), mcqItem.get(1), mcqItem.get(2), mcqItem.get(3), mcqItem.get(4), Integer.parseInt(mcqItem.get(5)));
            Integer authorIdx = Integer.parseInt(mcqItem.get(6)), courseIdx = Integer.parseInt(mcqItem.get(7));
            itemDao.createItem(mcqItemObj, authorList.get(authorIdx), courseIdx);
        }

        for (List<String> tfItem: trueFalseItems) {
            TrueFalseItem tfItemObj = new TrueFalseItem(tfItem.get(0), Boolean.parseBoolean(tfItem.get(1)));
            Integer authorIdx = Integer.parseInt(tfItem.get(2)), courseIdx = Integer.parseInt(tfItem.get(3));
            itemDao.createItem(tfItemObj, authorList.get(authorIdx), courseIdx);
        }
    }
}

