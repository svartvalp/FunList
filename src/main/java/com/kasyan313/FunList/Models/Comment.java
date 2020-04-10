package com.kasyan313.FunList.Models;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comment")
@JsonRootName("comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "post_id")
    private int postId;
    @Column(name = "text")
    private String text;
    @Column(name = "comment_time")
    private Timestamp commentTime;

    public Comment() {
    }

    public Comment(int userId, int postId, String text, Timestamp commentTime) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.commentTime = commentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }
}
