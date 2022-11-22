// TODO: Add this later
// @Override
// public Item updateAuthorItem(Item item, Integer authorId);

package in.ac.iiitb.itemService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.models.Item;
import in.ac.iiitb.itemService.repositories.AuthorRepository;
import in.ac.iiitb.itemService.repositories.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuthorRepository authorRepository;

    /*
        Create an Item object in the Database
        Return the created object
    */
    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    } 

    /*
        Return all the Courses in the Database
    */
    
    @Override
    public List<Item> getAuthorItems(int authorId) {
        return itemRepository.findByAuthorId(authorId);
    }
}

    