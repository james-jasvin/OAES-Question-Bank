package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Authors;

public interface HandlerDao {
    Authors handle(String loginId,String password);
}
