package com.kasyan313.FunList.Controllers;

import com.kasyan313.FunList.Exceptions.ValidationFailedException;
import com.kasyan313.FunList.MessageEntities.TokenEntity;
import com.kasyan313.FunList.Models.Post;
import com.kasyan313.FunList.Models.User;
import com.kasyan313.FunList.Security.JwtUtils;
import com.kasyan313.FunList.Services.PostService;
import com.kasyan313.FunList.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController()
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PostService postService;

    @GetMapping(value = "")
    public User getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.split(" ")[1];
        User user = userService.findUserById(jwtUtils.extractId(token));
        return  user;
    }

    @GetMapping("/{username}")
    public User getUserByUserName(@PathVariable String username) {
        return  userService.findUserByUserName(username);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public TokenEntity createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException();
        }
        System.out.println(user.getUsername() + " " + user.getPassword());
       User createdUser = userService.createUser(user.getUsername(), user.getPassword(), user.getEmail());
       return  new TokenEntity(jwtUtils.generateToken(createdUser));
    }

    @PutMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TokenEntity validateUser(@RequestBody User user) {
        System.out.println(user.getUsername() + " " + user.getPassword());
        User foundUser = userService.validateUser(user.getUsername(), user.getPassword());
        return new TokenEntity(jwtUtils.generateToken(foundUser));
    }

    @GetMapping(value = "/posts")
    public List<Post> getPostsByUser(@RequestHeader("Authorization") String authHeader,
                                     @RequestParam(value = "limit", defaultValue = "0", required = false) int limit,
                                     @RequestParam(value = "offset", defaultValue = "0", required = false) int offset) {

        String token = authHeader.split(" ")[1];
        return postService.getUserPosts(jwtUtils.extractId(token), limit, offset);
    }

    @DeleteMapping(value = "/{username}")
    public void deleteUser(@RequestHeader(value = "Authorization")  String authHeader, @PathVariable("username") String username) {

    }
}
