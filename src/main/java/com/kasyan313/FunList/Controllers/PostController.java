package com.kasyan313.FunList.Controllers;

import com.kasyan313.FunList.Models.Post;
import com.kasyan313.FunList.Security.JwtUtils;
import com.kasyan313.FunList.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RequestMapping(value = "/posts")
@RestController
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(value = "")
    public List<Post> getLastPosts(@RequestParam(value = "limit", defaultValue = "20", required = false) int limit,
                                   @RequestParam(value = "offset", defaultValue = "0", required = false) int offset) {
        return postService.getLastPosts(limit, offset);
    }
    @GetMapping(value = "/{timestamp}")
    public List<Post> getPostsBeforeTimestamp(@PathVariable Timestamp timestamp,
                                              @RequestParam(value = "limit", defaultValue = "20", required = false) int limit,
                                              @RequestParam(value = "offset", defaultValue = "0", required = false) int offset) {
        return postService.getPostsBeforeTimestamp(timestamp, limit, offset);
    }
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createPost(@RequestBody Post post, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.split(" ")[1];
        postService.createPost(jwtUtils.extractId(token), post.getText());
    }
    @DeleteMapping(value = "/{postId}")
    public void deletePost(@PathVariable int postId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.split(" ")[1];
        Post post = postService.getPostById(postId);
        if(post.getUserId() == jwtUtils.extractId(token)) {
            postService.deletePost(postId);
        }
    }
}
