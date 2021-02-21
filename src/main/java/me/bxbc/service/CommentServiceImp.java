package me.bxbc.service;

import me.bxbc.dao.CommentData;
import me.bxbc.obj.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/17
 */

@Service
public class CommentServiceImp implements CommentService{

    // 存放迭代找出的所有子类的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Autowired
    private CommentData commentData;

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentData.findByBlogIdAndFatherCommentsNull(id, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment commentSave(Comment comment) {
        Long fatherCommentId = comment.getFatherComments().getId();
        if(fatherCommentId != -1) {
            comment.setFatherComments(commentData.getOne(fatherCommentId));
        } else {
            comment.setFatherComments(null);
        }
        comment.setCreateTime(new Date());
        return commentData.save(comment);
    }

    /**
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根结点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {
        for(Comment comment : comments) {
            List<Comment> r1 = comment.getReply();
            for(Comment r2 : r1) {
                recursiveFind(r2);
            }
            comment.setReply(tempReplys);
            tempReplys = new ArrayList<>();
        }

    }

    /**
     * @param comment 被迭代的对象
     * @return
     */
    private void recursiveFind(Comment comment) {
        tempReplys.add(comment);
        if(comment.getReply().size()>0) {
            List<Comment> Replys = comment.getReply();
            for(Comment reply:Replys) {
                tempReplys.add(reply);
                if(reply.getReply().size()>0) {
                    recursiveFind(reply);
                }
            }
        }
    }

    @Override
    public void deleteComments(Long id) {
        commentData.deleteAllByBlogId(id);
        return;
    }

    @Override
    public void closeFK() {
        commentData.closeForeignKeyCheck();
    }

    @Override
    public void openFK() {
        commentData.openForeignKeyCheck();
    }
}
