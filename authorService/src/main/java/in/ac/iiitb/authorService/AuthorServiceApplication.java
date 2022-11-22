package in.ac.iiitb.authorService;

import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import in.ac.iiitb.authorService.models.Author;
import in.ac.iiitb.authorService.models.Course;
import in.ac.iiitb.authorService.models.Item;
import in.ac.iiitb.authorService.models.MCQItem;
import in.ac.iiitb.authorService.models.TrueFalseItem;

import in.ac.iiitb.authorService.repositories.AuthorRepository;
import in.ac.iiitb.authorService.repositories.CourseRepository;
import in.ac.iiitb.authorService.repositories.ItemRepository;


@SpringBootApplication
@EnableEurekaClient
public class AuthorServiceApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuthorServiceApplication.class, args);

	}

	    

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ItemRepository itemRepository;
	
	
    @Override
    public void run(String... args) throws Exception
    {
        // clearDatabase();

        List<Author> authors = Arrays.asList(new Author[] {
            new Author("Jasvin James", "jasvin_james", "Jasvin@123"),
            new Author("Gaurav Tirodkar","gaurav_tirodkar","Gaurav@123"),
            new Author( "Niraj Gujarathi","niraj_gujarathi","Niraj@123"),
            new Author("Arjun Dutta","arjun_dutta","Arjun@123")
        });

        List<Course> courses = Arrays.asList(new Course[] {
          new Course("Software Architecture", "SA"),
          new Course("Software Testing","ST"),
          new Course("Network Science for the Web","NSW"),
          new Course("Advanced Cyber Security","ACY"),
        });


		List<Item> itemlist= new ArrayList<>();
		Item i1= new Item(1,"abc","MCQ",courses.get(1),authors.get(0));
		itemlist.add(i1);



        // List<Item> mcqItems = Arrays.asList(new MCQItem[] {
        //   new MCQItem(
        //   1, 
        //   "In which of the following patterns an interface is responsible for creating a factory of related objects without explicitly specifying their classes?",
        //   "Factory Pattern",
        //   "Abstract Factory Pattern",
        //   "Singleton Pattern",
        //   "Transfer Object Pattern",
        //   2,
        //   authors.get(0),
        //   courses.get(1)
        //   ),
		  
        // //   new MCQItem(
        // //   2, 
        // //   "Which of the below is not a valid classification of design pattern?",
        // //   "Creational Patterns",
        // //   "Structural Patterns",
        // //   "Behavioural Patterns",
        // //   "J2EE Patterns",
        // //   4,
        // //   authors.get(0),
        // //   courses.get(1)
        // //   )
        // });
		authors.get(0).setItems(itemlist);
		courses.get(1).setItems(itemlist);


        // List<TrueFalseItem> trueFalseItems = Arrays.asList(new TrueFalseItem[] {
        //   new TrueFalseItem(
        //   3, 
        //   "Is the Earth Flat?",
        //   false,
        //   authors.get(2),
        //   courses.get(2)
        //   ),
        //   new TrueFalseItem(
        //   4, 
        //   "Can design patterns be used in Architecture as well?",
        //   true,
        //   authors.get(3),
        //   courses.get(3)
        //   )
        // });

        // Input format: (Question, Options, Answer, authorListIndex, courseId (from DB, starts at id = 1))


        // Input format: (Question, Answer, authorListIndex, courseId (from DB, starts at id = 1))

        for (Course course: courses)
			courseRepository.save(course);

        for (Author author: authors)
        	authorRepository.save(author);

        for (Item item: itemlist)
        	itemRepository.save(item);

        // for (TrueFalseItem trueFalseItem: trueFalseItems)
        // 	itemRepository.save(trueFalseItem);
    }

	
}