package in.ac.iiitb.itemService.service;

import java.util.List;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.models.Item;

public interface ItemService {
  Item createItem(Item item);
  List<Item> getAuthorItems(int authorId);
  // Item updateAuthorItem(Item item, Integer authorId);
}

