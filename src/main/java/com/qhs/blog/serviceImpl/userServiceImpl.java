package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.User;
import com.qhs.blog.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by QHS on 2017/5/27.
 */
@Service
public class userServiceImpl implements userService{

    @Override
    public boolean userReg(User user) {

        return false;
    }

    @Override
    public boolean userLogin(User user) {
        return false;
    }

    @Override
    public boolean userLogout(User user) {
        return false;
    }

    @Override
    public boolean userEdit() {
        return false;
    }
}
