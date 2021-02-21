package me.bxbc.service;

import me.bxbc.obj.Comment;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/17
 */

public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);
    Comment commentSave(Comment comment);
    void deleteComments(Long id);

    void closeFK();

    void openFK();
}
