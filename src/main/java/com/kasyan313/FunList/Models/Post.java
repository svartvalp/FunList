package com.kasyan313.FunList.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post")
@JsonRootName("post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    @JsonProperty(value = "user_id")
    private int userId;
    @Column(name = "text")
    @JsonProperty(value = "text")
    private String text;
    @JsonProperty(value = "post_time")
    @Column(name = "post_time")
    private Timestamp postTime;

    public Post() {
    }

    public Post(int userId, String text, Timestamp postTime) {
        this.userId = userId;
        this.text = text;
        this.postTime = postTime;
    }

    @JsonProperty(value = "id")
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }
}
