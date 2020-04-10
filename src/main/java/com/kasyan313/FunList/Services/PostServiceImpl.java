package com.kasyan313.FunList.Services;

import com.kasyan313.FunList.Exceptions.PostNotFoundException;
import com.kasyan313.FunList.Models.Post;
import com.kasyan313.FunList.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Component("postService")
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    SessionFactory sessionFactoryBean;
    @Autowired
    UserService userService;

    private Session getSession() {
        return sessionFactoryBean.getCurrentSession();
    }

    @Override
    public Post getPostById(int id) {
        try {
        Session session = getSession();
        Query<Post> query = session.createQuery("from  Post where id = :id");
        query.setParameter("id", id);
        Post post = query.getSingleResult();
        return post;
        } catch (Exception e) {
            throw new PostNotFoundException();
        }
    }

    @Override
    public List<Post> getLastPosts(int limit, int offset) {
            Session session = getSession();
            Query<Post> query = session.createQuery("from Post order by postTime desc", Post.class).setMaxResults(limit).setFirstResult(offset);
            List<Post> posts = query.list();
            return posts;
    }

    @Override
    public List<Post> getPostsBeforeTimestamp(Timestamp timestamp, int limit, int offset) {
        Session session = getSession();
        Query<Post> query = session.createQuery("from Post where postTime < :timestamp order by postTime desc", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit);
        query.setParameter("timestamp", timestamp);
        List<Post> posts = query.list();
        return posts;
    }

    @Override
    public List<Post> getUserPosts(int userId, int limit, int offset) {
        Session session = getSession();
        Query<Post> query = session.createQuery("from Post where userId = :userId " +
                "order by postTime desc", Post.class)
                .setMaxResults(limit)
                .setFirstResult(offset);
        query.setParameter("userId", userId);
        List<Post> posts = query.list();
        return posts;
    }

    @Override
    public List<Post> getUserPosts(String username, int limit, int offset) {
        Session session = getSession();
        User user = userService.findUserByUserName(username);
        Query<Post> query = session.createQuery("from Post where userId = :userId " +
                "order by postTime desc", Post.class)
                .setMaxResults(limit)
                .setFirstResult(offset);
        query.setParameter("userId", user.getId());
        return query.list();
    }

    @Override
    public void createPost(int userId, String text) {
        Session session = getSession();
        Post post = new Post();
        post.setText(text);
        post.setUserId(userId);
        post.setPostTime(new Timestamp(DateTime.now().toDate().getTime()));
        session.save(post);
    }

    @Override
    public void createPost(String username, String text) {
       Session session = getSession();
       User user = userService.findUserByUserName(username);
       Post post = new Post(user.getId(), text, new Timestamp(DateTime.now().toDate().getTime()));
       session.save(post);
    }

    @Override
    public void deletePost(int id) {
        Session session = getSession();
        Post post = getPostById(id);
        session.delete(post);
    }
}
