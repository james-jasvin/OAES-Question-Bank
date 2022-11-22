package in.ac.iiitb.itemService.service;

import java.util.List;

import in.ac.iiitb.itemService.models.Author;

public interface AuthorService {
  Author registerAuthor(Author author);
  Author loginAuthor(Author author);
  List<Author> getAuthors();
}
