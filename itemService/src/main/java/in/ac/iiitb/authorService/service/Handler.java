package in.ac.iiitb.authorService.service;

import in.ac.iiitb.authorService.models.Author;

public interface Handler {
    Author handle(Author author);
    void setNext(Handler handler);
}
