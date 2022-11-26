package in.ac.iiitb.itemService.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.ac.iiitb.itemService.models.Item;

public interface ItemService {
  Item createItem(String itemJSONString) throws JsonProcessingException;
  List<Item> getAuthorItems(Integer authorId);
  Item updateAuthorItem(String itemJSONString) throws JsonProcessingException;
}

