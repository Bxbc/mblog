package me.bxbc.dao;

import me.bxbc.obj.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: BI XI
 * Date 2021/2/8
 */
public interface UserData extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}
