package com.kasyan313.FunList.Services;
import com.kasyan313.FunList.Exceptions.CommentNotFoundException;
import com.kasyan313.FunList.Models.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Component("commentService")
@Transactional
public class CommentServiceImpl implements CommentService{
    @Autowired
    SessionFactory sessionFactoryBean;

    public Session getSession() {
        return sessionFactoryBean.getCurrentSession();
    }

    @Override
    public List<Comment> findCommentsByPost(int postId, int limit, int offset) {
        Session session = getSession();
        Query<Comment> query = session.createQuery("from Comment where postId = :postId " +
                "order by commentTime desc ", Comment.class)
                .setFirstResult(offset)
                .setMaxResults(limit);
        query.setParameter("postId", postId);
        return query.list();
    }

    @Override
    public void createComment(int postId, String text, int userId) {
        Session session = getSession();
        Comment comment = new Comment(userId, postId, text, new Timestamp(DateTime.now().toDate().getTime()));
        session.save(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        Session session = getSession();
        Comment comment = new Comment();
        comment.setId(commentId);
        session.delete(comment);
    }

    @Override
    public Comment findCommentById(int commentId) {
        try {
            Session session = getSession();
            Query<Comment> query = session.createQuery("from Comment where id = :commentId", Comment.class);
            query.setParameter("commentId", commentId);
            Comment comment = query.getSingleResult();
            return comment;
        } catch (Exception e) {
            throw new CommentNotFoundException();
        }
    }
}
