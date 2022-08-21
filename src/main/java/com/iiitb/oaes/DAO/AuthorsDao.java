package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Authors;
import java.util.List;

public interface AuthorsDao {
    boolean registerAuthor(Authors author);
    Authors loginAuthor(String loginId, String password);
    List<Authors> getAuthors();

//    Authors getAuthorsByID(String authorid);
}
