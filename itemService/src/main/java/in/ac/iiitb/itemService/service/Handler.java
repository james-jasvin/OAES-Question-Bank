package in.ac.iiitb.itemService.service;

import in.ac.iiitb.itemService.models.Author;

public interface Handler {
    Author handle(Author author);
    void setNext(Handler handler);
}
