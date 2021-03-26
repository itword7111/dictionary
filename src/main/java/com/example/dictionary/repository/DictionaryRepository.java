package com.example.dictionary.repository;

import com.example.dictionary.Entity.Words;

import java.util.List;

public interface DictionaryRepository {
    List<Words> getWordByValue(String value);
    Words get(Integer id);

    List<Words> getAll();

    Integer post(Words words);

    void remove(Integer id);
    void deleteValueByKey(Integer id, Integer wordId);
}
