package me.bxbc.web;


import me.bxbc.obj.Comment;
import me.bxbc.obj.User;
import me.bxbc.service.BlogService;
import me.bxbc.service.CommentService;
import me.bxbc.util.AvatarHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Author: BI XI
 * Date 2021/2/17
 */

@Controller
public class CommentControl {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String commentsPost(Comment comment, HttpSession session) throws Exception{
        User user = (User)session.getAttribute("user");
        Long blogId = comment.getBlog().getId();
        if(user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setNickname(user.getNickname());
//            comment.setEmail(user.getEmail());
            comment.setAdminComment(true);
        } else {
            int newid = blogId.intValue();
            String avatar = AvatarHelper.BASE64_PREFIX + AvatarHelper.createBase64Avatar(newid);
            comment.setAvatar(avatar);
        }
        comment.setBlog(blogService.getBlog(blogId));
        commentService.commentSave(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}
