package me.bxbc.service;

import me.bxbc.dao.UserData;
import me.bxbc.obj.User;
import me.bxbc.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: BI XI
 * Date 2021/2/8
 */

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserData userData;
    @Override
    public User checkUser(String username, String password) {
//        System.out.println(MD5Utils.encode(password));
        User user = userData.findByUsernameAndPassword(username, MD5Utils.encode(password));
        return user;
    }
}
