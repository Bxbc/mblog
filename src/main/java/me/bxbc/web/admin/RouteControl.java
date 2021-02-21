package me.bxbc.web.admin;

import me.bxbc.obj.User;
import me.bxbc.service.BlogService;
import me.bxbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Author: BI XI
 * Date 2021/2/8
 */

@Controller
@RequestMapping("/admin")
public class RouteControl {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if(user == null) {
            attributes.addFlashAttribute("message", "用户名或者密码错误");
            // System.out.println("wrong");
            return "redirect:/admin";
        } else {
            user.setPassword(null);
            session.setAttribute("user", user);
            // System.out.println("ok");
            return "/admin/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/footer/newestblog")
    public String adminnewestBlog(Model model) {
        model.addAttribute("newblogs", blogService.listBlog(3));
        return "/admin/_frags :: newestBlog";
    }
}
