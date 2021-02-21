package me.bxbc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: BI XI
 * Date 2021/2/19
 */

@Controller
public class ProfileControl {

    @GetMapping("/myprofile")
    public String myProfile() {
        return "profile";
    }
}
