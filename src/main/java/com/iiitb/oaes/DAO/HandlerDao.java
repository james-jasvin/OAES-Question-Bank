package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Author;

public interface HandlerDao {
    Author handle(String loginId,String password);
}
