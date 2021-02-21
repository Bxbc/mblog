package me.bxbc.service;

import me.bxbc.obj.User;

/**
 * Author: BI XI
 * Date 2021/2/8
 */

public interface UserService {
    User checkUser(String username, String password);
}
