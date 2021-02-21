package me.bxbc.obj;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/7
 */

@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;

    private boolean adminComment;

    @ManyToOne
    private Blog blog;

    @OneToMany(mappedBy = "fatherComments")
    private List<Comment> reply = new ArrayList<>();

    @ManyToOne
    private Comment fatherComments;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Comment() {
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", adminComment=" + adminComment +
                ", blog=" + blog +
                ", reply=" + reply +
                ", fatherComments=" + fatherComments +
                ", createTime=" + createTime +
                '}';
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReply() {
        return reply;
    }

    public void setReply(List<Comment> reply) {
        this.reply = reply;
    }

    public Comment getFatherComments() {
        return fatherComments;
    }

    public void setFatherComments(Comment fatherComments) {
        this.fatherComments = fatherComments;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
