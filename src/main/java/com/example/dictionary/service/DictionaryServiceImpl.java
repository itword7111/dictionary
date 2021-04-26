package com.example.dictionary.service;

import com.example.dictionary.entity.Translation;
import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;
import com.example.dictionary.repository.DictionaryRepository;
import com.example.dictionary.validation.DictionaryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryRepository repository;
    @Autowired
    private DictionaryValidator validator;

    @Override
    public Map<TypeOfDictionary, List<Word>> getAllWords() {
        List<Word> listOfEntry = repository.getAllWords();

        return listToMap(listOfEntry);
    }

    @Override
    public Map<TypeOfDictionary, List<Word>> getWordsByTranslation(String translation, TypeOfDictionary type, String value) {
        return listToMap(repository.getWordsByTranslation(translation, type, value));
    }

    private Map<TypeOfDictionary, List<Word>> listToMap(List<Word> list) {
        Map<TypeOfDictionary, List<Word>> dictionaries = new HashMap<>();
        for (Word word : list
        ) {
            if (!dictionaries.containsKey(word.getType())) {
                dictionaries.put(word.getType(), new LinkedList<>());
            }
            dictionaries.get(word.getType()).add(word);

        }
        return dictionaries;
    }

    @Override
    public Integer updateOrCreateWord(Word word) {
        if (validator.validation(word.getValue(),word.getType())) {
            for (Translation value : word.getTranslations()) {
                if (value.getValue().equals("")) {
                    return word.getId();
                }
            }
            return repository.updateOrCreateWord(word);
        }
        return null;
    }

    @Override
    public Word getWordById(Integer wordId) {
        return repository.getWordById(wordId);
    }

    @Override
    public void deleteWord(Integer wordId) {
        repository.deleteWord(wordId);
    }

    @Override
    public void deleteTranslationByWord(Integer translationId, Integer wordId) {
        repository.deleteTranslationByWord(translationId, wordId);
    }

}
