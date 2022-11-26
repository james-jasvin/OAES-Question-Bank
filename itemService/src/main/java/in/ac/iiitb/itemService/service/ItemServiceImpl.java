package in.ac.iiitb.itemService.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.models.Course;
import in.ac.iiitb.itemService.models.Item;
import in.ac.iiitb.itemService.models.MCQItem;
import in.ac.iiitb.itemService.models.TrueFalseItem;

import in.ac.iiitb.itemService.repositories.CourseRepository;
import in.ac.iiitb.itemService.repositories.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /*
        Create an Item object in the Database
        Return the created object
    */
    @Override
    public Item createItem(String itemJSONString) throws JsonProcessingException {
        JSONObject itemJSON = new JSONObject(itemJSONString);
        
        // Cast Item to correct type, MCQ or TrueFalse
        Item castedItem = getItemFromJSON(itemJSON);
        
        Author author = objectMapper.readValue(
            itemJSON.get("author").toString(), 
            Author.class
        );

        Integer courseId = (Integer) itemJSON.get("courseId");

        // TODO: Add Login Mechanism HERE

        Optional<Course> course = courseRepository.findById(courseId);
        
        // If invalid courseId specified which is why course is null, item creation fails
        if (!course.isPresent())
            return null;

        // Trim the question to prevent empty string questions from being inserted
        castedItem.setQuestion(castedItem.getQuestion().trim());

        // Must similarly trim other attributes later

        // TODO: It should be loggedInAuthor here
        castedItem.setAuthor(author);

        castedItem.setCourse(course.get());

        try {
            return itemRepository.save(castedItem);
        }
        // In case any DB constraints are violated while inserting
        catch (DataIntegrityViolationException e) {
            return null;
        }
    } 

    /*
        Update an existing Item with the details specified in the input
        If a field is to be left unchanged, that field will be set as null in the input

        - Fetch the database Item
        - Set all the fields in the DB Item from the updatedItem (input data) if the corresponding field is non-null
        - For this you need separate utility methods for handling each type of Item, MCQ/TrueFalse/etc.
    */
    @Override
    public Item updateAuthorItem(String itemJSONString) throws JsonProcessingException {
        JSONObject itemUpdationJSON = new JSONObject(itemJSONString);

        Item updatedItem = getItemFromJSON(itemUpdationJSON);
        
        Author author = objectMapper.readValue(
            itemUpdationJSON.get("author").toString(), 
            Author.class
        );

        // TODO: ADD LOGIN HERE

        Optional<Item> optionalDBItem = itemRepository.findById(updatedItem.getItemId());

        if (optionalDBItem.isEmpty())
            return null;

        Item dbItem = optionalDBItem.get();

        // Question is common attribute to all Items, so its handled here
        if (updatedItem.getQuestion() != null)
            dbItem.setQuestion(updatedItem.getQuestion());
        
        // Else-If Ladder for determining Item Type and calling appropriate update function
        if (updatedItem.getItemType().equals("MCQ")) {
            MCQItem mcqUpdatedItem = (MCQItem) updatedItem, mcqDbItem = (MCQItem) dbItem;
            updateMCQItem(mcqUpdatedItem, mcqDbItem);
        }
        else {
            TrueFalseItem tfUpdatedItem = (TrueFalseItem) updatedItem, tfDbItem = (TrueFalseItem) dbItem;
            updateTrueFalseItem(tfUpdatedItem, tfDbItem);
        }
        
        // Update the DB item with the updated fields in the database
        try {
            return itemRepository.save(dbItem);
        }
        // In case any DB constraints are violated while inserting
        catch (UnexpectedRollbackException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        Return all the Author's Items in the Database
    */
    @Override
    public List<Item> getAuthorItems(Integer authorId) {
        // Create an empty Author object with just the authorId parameter filled in so that you
        // can execute the findByAuthor JPA query
        Author author = new Author();
        author.setAuthorId(authorId);

        return itemRepository.findByAuthor(author);
    }

    /*
        Utility method that takes in JSON string that represents an item and converts it to an object
        of the subclass representing the complete Item, i.e. MCQItem, TrueFalseItem, etc.
    */
    public Item getItemFromJSON(JSONObject itemJSON) throws JsonProcessingException {
        // Class<?> for holding a reference to a class variable of any Class, since we do not know the concrete
        // subclass in advance
        Class<?> itemClass;
        String itemType = "";

        // This is why itemJSON must have itemType as an input parameter from the front-end, otherwise there's no
        // direct way to know what Item is specified in the input JSON, MCQ/True False/etc.
        switch (itemJSON.get("itemType").toString()) {
            case "MCQ":
                itemClass = MCQItem.class;
                itemType = "MCQ";
                break;

            case "TrueFalse":
                itemClass = TrueFalseItem.class;
                itemType = "TrueFalse";
                break;

            default:
                itemClass = MCQItem.class;
        }

        // Convert itemJSON into item object of the relevant class using Jackson ObjectMapper
        Item item = (Item) objectMapper.readValue(itemJSON.get("item").toString(), itemClass);

        // Also important step for the DAO methods
        item.setItemType(itemType);

        return item;
    }

    // Only update those fields in dbItem which are non-NULL in updatedItem
    // Same thing has to be repeated for all Item Types

    public void updateMCQItem(MCQItem updatedItem, MCQItem dbItem) {
        if (updatedItem.getOption1() != null)
            dbItem.setOption1(updatedItem.getOption1());

        if (updatedItem.getOption2() != null)
            dbItem.setOption2(updatedItem.getOption2());

        if (updatedItem.getOption3() != null)
            dbItem.setOption3(updatedItem.getOption3());

        if (updatedItem.getOption4() != null)
            dbItem.setOption4(updatedItem.getOption4());

        if (updatedItem.getAnswer() != null)
            dbItem.setAnswer(updatedItem.getAnswer());
    }

    public void updateTrueFalseItem(TrueFalseItem updatedItem, TrueFalseItem dbItem) {
        if (updatedItem.getAnswer() != null)
            dbItem.setAnswer(updatedItem.getAnswer());
    }
}

    