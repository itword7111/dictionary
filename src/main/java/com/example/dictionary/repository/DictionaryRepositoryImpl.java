package com.example.dictionary.repository;

import com.example.dictionary.entity.Translation;
import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;
import org.hibernate.Criteria;
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
    public List<Word> getAllWords() {

        Criteria word = currentSession().createCriteria(Word.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return word.list();
    }

    @Override
    public List<Word> getWordsByTranslation(String translation, TypeOfDictionary type, String value) {
        Criteria word = currentSession().createCriteria(Word.class);
        if (!value.equals("")) {
            word.add(Restrictions.like("value", value))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            return word.list();
        }
        if(type!=null) {
            word.add(Restrictions.like("type", type));
        }
        if(!translation.equals("")) {
            word.createCriteria("translations")
                    .add(Restrictions.like("value", translation));
        }

        return word.list();
    }

    @Override
    public Word getWordById(Integer wordId) {
        return currentSession().get(Word.class, wordId);
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    private Integer createNewWord(Word words, Session session) {
        session.save(words);
        return words.getId();
    }

    private Integer update(Word words, Session session) {
        Word wordForUpdate;
        wordForUpdate = session.get(Word.class, words.getId());
        wordForUpdate.setValue(words.getValue());
        wordForUpdate.setType(words.getType());
        wordForUpdate.getTranslations().addAll(words.getTranslations());
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
    public void deleteWord(Integer wordId) {
        currentSession().delete(currentSession().get(Word.class, wordId));
    }

    @Override
    public void deleteTranslationByWord(Integer translationId, Integer wordId) {
        Word word = currentSession().get(Word.class, wordId);
        Collection<Translation> values = word.getTranslations();
        for (Translation value : values) {
            if (value.getId().equals(translationId)) {
                values.remove(value);
                currentSession().save(word);
                return;
            }
        }
    }

}
