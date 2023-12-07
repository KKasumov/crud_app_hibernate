package com.kyamran.app.repository.hibernateImpl;

import com.kyamran.app.model.Post;
import com.kyamran.app.model.PostStatus;
import com.kyamran.app.model.Writer;
import com.kyamran.app.repository.PostRepository;
import com.kyamran.app.utilities.DateFormatter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private static PostRepositoryImpl instance;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private PostRepositoryImpl() {
    }

    public static PostRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PostRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Post save(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        post.setPostStatus(PostStatus.ACTIVE);
        post.setUpdated(DateFormatter.getCurrentDate());
        post.setCreated(DateFormatter.getCurrentDate());

        Writer writer = post.getWriter();
        if (writer != null && writer.getId() == null) {
            session.save(writer);
        }

        session.save(post);
        transaction.commit();
        session.close();

        return post;
    }

    @Override
    public Post update(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        post.setPostStatus(PostStatus.UNDER_REVIEW);
        session.update(post);

        transaction.commit();
        session.close();

        return post;
    }

    @Override
    public Post getById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Post post = session.get(Post.class, id);

        transaction.commit();
        session.close();

        return post;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Post post = session.get(Post.class, id);
        if (post != null) {
            post.setPostStatus(PostStatus.DELETED);
            session.update(post);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public List<Post> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Post> posts = session.createQuery("FROM Post", Post.class).list();

        transaction.commit();
        session.close();

        return posts;
    }

}