package com.kasyan313.FunList.Services;

import com.kasyan313.FunList.Models.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> findCommentsByPost(int postId, int limit, int offset);
    public void createComment(int postId, String text, int userId);
    public void deleteComment(int commentId);
    public Comment findCommentById(int commentId);
}
