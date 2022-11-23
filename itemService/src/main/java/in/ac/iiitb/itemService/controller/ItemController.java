package in.ac.iiitb.itemService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.ac.iiitb.itemService.models.Item;
import in.ac.iiitb.itemService.service.ItemService;


@RestController
@CrossOrigin
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @GetMapping(
        produces = MediaType.APPLICATION_JSON
    )
    public ResponseEntity<List<Item>> getAuthorItems(@QueryParam("authorId") Integer authorId) {
        List<Item> itemsList = itemService.getAuthorItems(authorId);
        if (itemsList == null)
            return new ResponseEntity<List<Item>>(itemsList, HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<List<Item>>(itemsList, HttpStatus.OK);
    }

    @PostMapping(
      consumes=MediaType.APPLICATION_JSON,
      produces=MediaType.APPLICATION_JSON
    )
    public ResponseEntity<Item> createItem(@RequestBody String itemJSONString) throws JsonProcessingException {
        Item itemObj = itemService.createItem(itemJSONString);

        if (itemObj == null)
            return new ResponseEntity<Item>(itemObj, HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<Item>(itemObj, HttpStatus.CREATED);
    }
}