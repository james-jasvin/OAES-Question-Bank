// TODO: Add this later
// @Override
// public Item updateAuthorItem(Item item, Integer authorId);

package in.ac.iiitb.itemService.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return itemRepository.save(castedItem);
    } 

    /*
        Return all the Courses in the Database
    */
    @Override
    public List<Item> getAuthorItems(Integer authorId) {
        Author author = new Author(authorId, null, null, null, null);
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
}

    