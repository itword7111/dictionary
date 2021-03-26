package com.example.dictionary.service;

import com.example.dictionary.Entity.Words;

import java.util.List;
import java.util.Map;

public interface DictionaryService {
    Map<String,List<Words>> getWordByValue(String value);
    Integer add(Words words);

    Words get(Integer id);

    void deleteByKey(Integer id);

    Map<String,List<Words>> getAll();

    void deleteValueByKey(Integer id,Integer wordId);

}
