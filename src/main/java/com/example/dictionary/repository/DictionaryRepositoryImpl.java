package com.example.dictionary.repository;

import com.example.dictionary.Entity.Value;
import com.example.dictionary.Entity.Words;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DictionaryRepositoryImpl implements DictionaryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Words> getWordByValue(String value) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Words> words = session.createCriteria(Words.class).createCriteria("keys").add(Restrictions.like("value", value)).list();
            transaction.commit();
            return words;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Words get(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Words words = session.load(Words.class, id);
            transaction.commit();
            return words;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Words> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Words> words = (List<Words>) session.createCriteria(Words.class).list().stream().distinct().collect(Collectors.toList());
            transaction.commit();
            return words;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return null;
    }

    private Integer createNew(Words words, Session session) {
        if (!words.getKeys().iterator().next().equals("")) {
            for (Value value:words.getKeys()
            ) {
                value.setWords(new LinkedList<Words>());
                value.getWords().add(words);
                words.getKeys().add(value);
                session.save(value);
            }
        }
        session.save(words);
        return words.getId();
    }

    private Integer update(Words words, Session session) {
        Words word;
        word = session.load(Words.class, words.getId());
        word.setValue(words.getValue());
        word.setType(words.getType());
        if (!words.getKeys().iterator().next().equals("")) {
            for (Value value:words.getKeys()
                 ) {
                value.setWords(new LinkedList<Words>());
                value.getWords().add(word);
                word.getKeys().add(value);
                session.save(value);
            }
        }
        session.save(word);
        return word.getId();
    }

    @Override
    public Integer post(Words words) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer id = 0;
        try {
            transaction = session.beginTransaction();
            if (words.getId() != null) {
                id = update(words, session);
            } else {
                id = createNew(words, session);
            }

            transaction.commit();
            return id;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void remove(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(session.load(Words.class, id));
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }

    }

    @Override
    public void deleteValueByKey(Integer id, Integer wordId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Words words = session.load(Words.class, wordId);
            Collection<Value> values = words.getKeys();
            for (Value value : values
            ) {
                if (value.getId().equals(id)) {
                    values.remove(value);
                    session.save(words);
                    transaction.commit();
                    return;
                }
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
    }
}
