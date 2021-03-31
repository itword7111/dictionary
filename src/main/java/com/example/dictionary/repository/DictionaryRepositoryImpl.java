package com.example.dictionary.repository;

import com.example.dictionary.entity.Translation;
import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class DictionaryRepositoryImpl implements DictionaryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Word> getWordsByTranslation(String translation) {

        List<Word> words = currentSession().createCriteria(Word.class)
                .createCriteria("keys")
                .add(Restrictions.like("value", translation))
                .list();
        return words;
    }

    @Override
    public Word getWordById(Integer wordId) {
        return currentSession().get(Word.class, wordId);
    }

    @Override
    public List<Word> getAllWords() {
        List<Word> words = (List<Word>) currentSession().createCriteria(Word.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();

        return words;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    private Integer createNewWord(Word words, Session session) {
        for (Translation value : words.getKeys()
        ) {
            if (value.getValue().equals("")) {
                return words.getId();
            }
            session.save(value);
        }
        session.save(words);
        return words.getId();
    }

    private Integer update(Word words, Session session) {
        Word wordForUpdate;
        wordForUpdate = session.get(Word.class, words.getId());
        wordForUpdate.setValue(words.getValue());
        wordForUpdate.setType(words.getType());
        for (Translation value : words.getKeys()
        ) {
            if (value.getValue().equals("")) {
                return words.getId();
            }
            wordForUpdate.getKeys().add(value);
        }

        session.save(wordForUpdate);
        return wordForUpdate.getId();
    }

    @Override
    public Integer updateOrCreateWord(Word word) {
        Integer id;
        if (word.getId() != null) {
            id = update(word, currentSession());
        } else {
            id = createNewWord(word, currentSession());
        }
        return id;
    }

    @Override
    public List<Word> getWordsByTranslationByType(String translation, TypeOfDictionary type) {
        return currentSession().createCriteria(Word.class).add(Restrictions.like("type", type)).createCriteria("keys")
                .add(Restrictions.like("value", translation))
                .list();
    }

    @Override
    public void deleteWord(Integer wordId) {
        currentSession().delete(currentSession().get(Word.class, wordId));
    }

    @Override
    public void deleteTranslationByWord(Integer translationId, Integer wordId) {
        Word word = currentSession().get(Word.class, wordId);
        Collection<Translation> values = word.getKeys();
        for (Translation value : values
        ) {
            if (value.getId().equals(translationId)) {
                values.remove(value);
                currentSession().save(word);
                return;
            }
        }
    }

    @Override
    public List<Word> getByWordValue(String value) {
        return (List<Word>) currentSession().createCriteria(Word.class)
                .add(Restrictions.like("value", value))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
