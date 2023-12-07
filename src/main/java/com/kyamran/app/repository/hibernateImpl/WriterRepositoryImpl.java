package com.kyamran.app.repository.hibernateImpl;

import com.kyamran.app.model.Writer;
import com.kyamran.app.repository.WriterRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private static WriterRepositoryImpl instance;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private WriterRepositoryImpl() {
    }

    public static WriterRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new WriterRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Writer save(Writer writer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(writer);

        transaction.commit();
        session.close();

        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(writer);

        transaction.commit();
        session.close();

        return writer;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Writer writer = session.get(Writer.class, id);
        if (writer != null) {
            session.delete(writer);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public Writer getById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Writer writer = session.get(Writer.class, id);

        transaction.commit();
        session.close();

        return writer;
    }

    @Override
    public List<Writer> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Writer> writers = session.createQuery("FROM Writer", Writer.class).list();

        transaction.commit();
        session.close();

        return writers;
    }

}