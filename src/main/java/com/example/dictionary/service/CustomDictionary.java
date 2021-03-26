package com.example.dictionary.service;

import com.example.dictionary.Entity.Words;
import com.example.dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomDictionary implements DictionaryService {
    @Autowired
    private DictionaryRepository repository;

    @Override
    public Map<String, List<Words>> getWordByValue(String value) {
        return listToMap(repository.getWordByValue(value));
    }

    @Override
    public Integer add(Words words) {
        return repository.post(words);
    }

    @Override
    public Words get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Map<String, List<Words>> getAll() {
        List<Words> listOfEntry = repository.getAll();

        return listToMap(listOfEntry);
    }

    @Override
    public void deleteValueByKey(Integer id, Integer wordId) {
        repository.deleteValueByKey(id, wordId);
    }

    private Map<String, List<Words>> listToMap(List<Words> list) {
        Map<String, List<Words>> dictionaryes = new HashMap<>();
        for (Words word : list
        ) {
            if (dictionaryes.containsKey(word.getType()) == false) {
                dictionaryes.put(word.getType(), new LinkedList<>());
                dictionaryes.get(word.getType()).add(word);
            } else dictionaryes.get(word.getType()).add(word);

        }
        return dictionaryes;
    }

    @Override
    public void deleteByKey(Integer id) {
        repository.remove(id);
    }

}
