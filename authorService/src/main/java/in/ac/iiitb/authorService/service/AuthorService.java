package in.ac.iiitb.authorService.service;

import java.util.List;

import in.ac.iiitb.authorService.models.Author;

public interface AuthorService {
  Author registerAuthor(Author author);
  Author loginAuthor(Author author);
  List<Author> getAuthors();
}
