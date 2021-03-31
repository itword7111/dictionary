package com.example.dictionary.service;

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
    public Map<String, List<Word>> getWordsByTranslation(String translation) {
        return listToMap(repository.getWordsByTranslation(translation));
    }

    @Override
    public Integer updateOrCreateWord(Word word) {
        if (validator.validation(word.getValue(),word.getTypeEnum())) {
            return repository.updateOrCreateWord(word);
        }
        return 0;
    }

    @Override
    public Word getWordById(Integer wordId) {
        return repository.getWordById(wordId);
    }

    @Override
    public Map<String, List<Word>> getAllWords() {
        List<Word> listOfEntry = repository.getAllWords();

        return listToMap(listOfEntry);
    }

    @Override
    public List<Word> getWordsByTranslationByType(String translation, String type) {
        TypeOfDictionary typeOfDictionary=TypeOfDictionary.latin4;
        for (TypeOfDictionary typeOfDictionaryValue:TypeOfDictionary.values()
        ) {
            if(typeOfDictionaryValue.toString().equals(type)){
                typeOfDictionary=typeOfDictionaryValue;
                break;
            }
        }
        return repository.getWordsByTranslationByType(translation, typeOfDictionary);
    }

    @Override
    public void deleteTranslationByWord(Integer translationId, Integer wordId) {
        repository.deleteTranslationByWord(translationId, wordId);
    }

    @Override
    public List<Word> getByWordValue(String value) {
        return repository.getByWordValue(value);
    }

    private Map<String, List<Word>> listToMap(List<Word> list) {
        Map<String, List<Word>> dictionaries = new HashMap<>();
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
    public void deleteWord(Integer wordId) {
        repository.deleteWord(wordId);
    }

}
