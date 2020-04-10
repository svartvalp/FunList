package com.kasyan313.FunList.Controllers;

import com.kasyan313.FunList.Models.Comment;
import com.kasyan313.FunList.Security.JwtUtils;
import com.kasyan313.FunList.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    JwtUtils jwtUtils;

    @GetMapping(value = "/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable int postId,
                                           @RequestParam(value = "limit", defaultValue = "0", required = false) int limit,
                                           @RequestParam(value = "offset", defaultValue = "0", required = false) int offset) {
        return commentService.findCommentsByPost(postId, limit, offset);
    }

    @PostMapping(value = "")
    public void createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.split(" ")[1];
        commentService.createComment(comment.getPostId(), comment.getText(), jwtUtils.extractId(token));
    }

    @DeleteMapping(value = "/{commentId}")
    public void deleteComment(@PathVariable int commentId,  @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.split(" ")[1];
        Comment comment = commentService.findCommentById(commentId);
        if(comment.getUserId() == jwtUtils.extractId(token)) {
            commentService.deleteComment(commentId);
        }
    }
}
