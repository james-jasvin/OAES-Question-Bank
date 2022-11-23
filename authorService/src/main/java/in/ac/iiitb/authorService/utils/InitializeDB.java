package in.ac.iiitb.authorService.utils;

import in.ac.iiitb.authorService.models.Author;
import in.ac.iiitb.authorService.models.Course;
import in.ac.iiitb.authorService.models.Item;
import in.ac.iiitb.authorService.models.MCQItem;
import in.ac.iiitb.authorService.models.TrueFalseItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/*
    Utility class for setting the Database up with entries in all the tables

    For the clearDatabase() method to work, the "hibernate.hbm2ddl.auto" property in hibernate.cfg.xml has to be set to
    "create-drop", otherwise it will show dependency error while deleting Item table and you'll end up with empty database
 */

public class InitializeDB{
  public static Author createAuthor(Author author) {
    try (Session session = SessionUtil.getSession()) {
        Transaction transaction = session.beginTransaction();
        session.save(author);
        transaction.commit();
        return author;
    }
    catch (HibernateException exception) {
        System.out.print(exception.getLocalizedMessage());
        return null;
    }
  }

  public static Course createCourse(Course course) {
    try (Session session = SessionUtil.getSession()) {
        Transaction transaction = session.beginTransaction();
        session.save(course);
        transaction.commit();
        return course;
    }
    catch (HibernateException exception) {
        System.out.print(exception.getLocalizedMessage());
        return null;
    }
  }

  public static Item createItem(Item item) {
    try (Session session = SessionUtil.getSession()) {
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        return item;
    }
    catch (HibernateException exception) {
        System.out.print(exception.getLocalizedMessage());
        return null;
    }
  }

  public static void main(String[] args) {
      List<Author> authors = Arrays.asList(new Author[] {
          new Author("Jasvin James", "jasvin_james", "Jasvin@123"),
          new Author("Gaurav Tirodkar","gaurav_tirodkar","Gaurav@123"),
          new Author( "Niraj Gujarathi","niraj_gujarathi","Niraj@123"),
          new Author( "Arjun Dutta","arjun_dutta","Arjun@123")
      });

      List<Course> courses = Arrays.asList(new Course[] {
        new Course("Software Architecture", "SA", null),
        new Course("Software Testing","ST", null),
        new Course("Network Science for the Web","NSW", null),
        new Course("Advanced Cyber Security","ACY", null),
      });

      List<Course> dbCourses = new ArrayList<>();
      for (Course course: courses)
        dbCourses.add(createCourse(course));

      List<Author> dbAuthors = new ArrayList<>();
      for (Author author: authors)
          dbAuthors.add(createAuthor(author));

        List<MCQItem> mcqItems = Arrays.asList(new MCQItem[] {
          new MCQItem(
          "In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
          "Factory Pattern",
          "Abstract Factory Pattern",
          "Singleton Pattern",
          "Transfer Object Pattern",
          2,
          dbAuthors.get(0),
          dbCourses.get(0)
          ),
      
          new MCQItem(
          "Which of the below is not a valid classification of design pattern?",
          "Creational Patterns",
          "Structural Patterns",
          "Behavioural Patterns",
          "J2EE Patterns",
          4,
          dbAuthors.get(1),
          dbCourses.get(1)
          )
        });
  
  
        List<TrueFalseItem> trueFalseItems = Arrays.asList(new TrueFalseItem[] {
          new TrueFalseItem(
          "Is the Earth Flat?",
          false,
          dbAuthors.get(2),
          dbCourses.get(2)
          ),
          new TrueFalseItem(
          "Can design patterns be used in Architecture as well?",
          true,
          dbAuthors.get(3),
          dbCourses.get(3)
          )
        });

      for (MCQItem mcqItem: mcqItems)
        createItem(mcqItem);

      for (TrueFalseItem trueFalseItem: trueFalseItems)
        createItem(trueFalseItem);
  }
}

