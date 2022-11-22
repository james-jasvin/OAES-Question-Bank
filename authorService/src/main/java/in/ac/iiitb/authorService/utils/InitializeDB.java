package in.ac.iiitb.authorService.utils;

import in.ac.iiitb.authorService.models.Author;
import in.ac.iiitb.authorService.models.Course;
import in.ac.iiitb.authorService.models.MCQItem;
import in.ac.iiitb.authorService.models.TrueFalseItem;

import in.ac.iiitb.authorService.repositories.AuthorRepository;
import in.ac.iiitb.authorService.repositories.CourseRepository;
import in.ac.iiitb.authorService.repositories.ItemRepository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


/*
    Utility class for setting the Database up with entries in all the tables

    For the clearDatabase() method to work, the "hibernate.hbm2ddl.auto" property in hibernate.cfg.xml has to be set to
    "create-drop", otherwise it will show dependency error while deleting Item table and you'll end up with empty database
 */

public class InitializeDB{
    @Autowired
	private static AuthorRepository authorRepository;

	@Autowired
    private static CourseRepository courseRepository;

    @Autowired
    private static ItemRepository itemRepository;

   
    // public static void clearDatabase() {
    //     try (Session session = SessionUtil.getSession()) {
    //         Transaction transaction = session.beginTransaction();

    //         Query query = session.createQuery("delete from Item");
    //         query.executeUpdate();

    //         query = session.createQuery("delete from Course");
    //         query.executeUpdate();

    //         query = session.createQuery("delete from Author");
    //         query.executeUpdate();
    //         transaction.commit();
    //     }
    //     catch (HibernateException exception) {
    //         System.out.print(exception.getLocalizedMessage());
    //     }
    // }

    public static void main(String[] args)
    {
      
        // clearDatabase();

        List<Author> authors = Arrays.asList(new Author[] {
            new Author("Jasvin James", "jasvin_james", "Jasvin@123"),
            new Author("Gaurav Tirodkar","gaurav_tirodkar","Gaurav@123"),
            new Author( "Niraj Gujarathi","niraj_gujarathi","Niraj@123"),
            new Author( "Arjun Dutta","arjun_dutta","Arjun@123")
        });

        List<Course> courses = Arrays.asList(new Course[] {
          new Course(1, "Software Architecture", "SA", null),
          new Course(2, "Software Testing","ST", null),
          new Course(3, "Network Science for the Web","NSW", null),
          new Course(4, "Advanced Cyber Security","ACY", null),
        });

        List<MCQItem> mcqItems = Arrays.asList(new MCQItem[] {
          new MCQItem(
          1, 
          "In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
          "Factory Pattern",
          "Abstract Factory Pattern",
          "Singleton Pattern",
          "Transfer Object Pattern",
          2,
          authors.get(0),
          courses.get(0)
          ),
		  
          new MCQItem(
          2, 
          "Which of the below is not a valid classification of design pattern?",
          "Creational Patterns",
          "Structural Patterns",
          "Behavioural Patterns",
          "J2EE Patterns",
          4,
          authors.get(1),
          courses.get(1)
          )
        });


        List<TrueFalseItem> trueFalseItems = Arrays.asList(new TrueFalseItem[] {
          new TrueFalseItem(
          3, 
          "Is the Earth Flat?",
          false,
          authors.get(2),
          courses.get(2)
          ),
          new TrueFalseItem(
          4, 
          "Can design patterns be used in Architecture as well?",
          true,
          authors.get(3),
          courses.get(3)
          )
        });

        // Input format: (Question, Options, Answer, authorListIndex, courseId (from DB, starts at id = 1))


        // Input format: (Question, Answer, authorListIndex, courseId (from DB, starts at id = 1))

        for (Course course: courses)
			courseRepository.save(course);

        for (Author author: authors)
        	authorRepository.save(author);

        for (MCQItem mcqItem: mcqItems)
        	itemRepository.save(mcqItem);

        for (TrueFalseItem trueFalseItem: trueFalseItems)
        	itemRepository.save(trueFalseItem);
    
    }
}

