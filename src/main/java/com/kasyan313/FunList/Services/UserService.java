package com.kasyan313.FunList.Services;

import com.kasyan313.FunList.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    public User findUserById(int id);
    public User findUserByUserName(String username);
    public User createUser(String username, String password, String email);
    public void deleteUser(int id);
    public void updateUser(int id, String username, String password);
    public void changeEmail(int id, String newEmail);
    public User validateUser(String username, String password);
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
