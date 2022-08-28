package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Author;
import java.util.List;

public interface AuthorDao {
    boolean registerAuthor(Author author);
    Author loginAuthor(String loginId, String password);
    List<Author> getAuthors();

//    Authors getAuthorsByID(String authorid);
}
