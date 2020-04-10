package com.kasyan313.FunList.Services;

import com.kasyan313.FunList.Exceptions.UserNotFoundException;
import com.kasyan313.FunList.Models.User;
import com.kasyan313.FunList.UserDetailsImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.NoResultException;
import java.util.List;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    SessionFactory sessionFactoryBean;
    
    private Session getSession() {
        return sessionFactoryBean.getCurrentSession();
    }
    
    @Override
    public User findUserById(int id) {
        try {
            Session session = getSession();
            Query query = session.createQuery("from User where id = :id", User.class);
            query.setParameter("id", id);
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception exc) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User findUserByUserName(String username) {
        try{
            Session session = getSession();
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User createUser(String username, String password, String email) {
        Session session = getSession();
        User user = new User(username, password, email);
        session.save(user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        try {
            Session session = getSession();
            Query<User> query = session.createQuery("from User where id = :id", User.class);
            query.setParameter("id", id);
            User user = query.getSingleResult();
            session.delete(user);
        }catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateUser(int id, String username, String password) {
        Session session = getSession();
        Query<User> query = session.createQuery("update User set username = :username, password = :password " +
                "where id = :id", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void changeEmail(int id, String newEmail) {
        Session session = getSession();
        Query<User> query = session.createQuery("update User set email = :newEmail where id = :id", User.class);
        query.setParameter("newEmail", newEmail);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public User validateUser(String  username, String password) {
        try {
            Session session = getSession();
            Query<User> query = session.createQuery("from User where username = :username and " +
                    "password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            User user = query.getSingleResult();
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException("invalid username or password");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = getSession();
        Query<User> query = session.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        try {
            User user = query.getSingleResult();
            return new UserDetailsImpl(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
