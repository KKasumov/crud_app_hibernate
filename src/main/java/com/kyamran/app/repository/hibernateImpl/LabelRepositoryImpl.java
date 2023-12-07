package com.kyamran.app.repository.hibernateImpl;

import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;
import com.kyamran.app.repository.LabelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {
    private static LabelRepositoryImpl instance;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private LabelRepositoryImpl() {
    }

    public static LabelRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LabelRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Label save(Label label) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(label);

        transaction.commit();
        session.close();

        return label;
    }

    @Override
    public Label update(Label label) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(label);

        transaction.commit();
        session.close();

        return label;
    }

    @Override
    public Label getById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Label label = session.get(Label.class, id);

        transaction.commit();
        session.close();

        return label;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Label label = session.get(Label.class, id);
        if (label != null) {
            session.delete(label);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public List<Label> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Label> labels = session.createQuery("FROM Label", Label.class).list();

        transaction.commit();
        session.close();

        return labels;
    }
    @Override
    public void savePostLabels(Post post, List<Label> labels) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        for (Label label : labels) {
            if (label.getPosts() == null) {
                label.setPosts(new ArrayList<>());
            }
            label.getPosts().add(post);
            session.saveOrUpdate(label);
        }

        transaction.commit();
        session.close();
    }
}