package in.ac.iiitb.authorService.service;

import java.util.List;

import in.ac.iiitb.authorService.models.Author;
import in.ac.iiitb.authorService.models.Item;

public interface ItemService {
  Item createItem(Item item);
  List<Item> getAuthorItems(Author author);
  // Item updateAuthorItem(Item item, Integer authorId);
}

