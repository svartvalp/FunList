package com.kasyan313.FunList.Services;

import com.kasyan313.FunList.Models.Post;

import java.sql.Timestamp;
import java.util.List;

public interface PostService {
    public Post getPostById(int id);
    public List<Post> getLastPosts(int limit, int offset);
    public List<Post> getPostsBeforeTimestamp(Timestamp timestamp, int limit, int offset);
    public List<Post> getUserPosts(int userId, int limit, int offset);
    public List<Post> getUserPosts(String username, int limit, int offset);
    public void createPost(int userId, String text);
    public void createPost(String username, String text);
    public void deletePost(int id);
}
