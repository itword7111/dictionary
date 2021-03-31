package com.example.dictionary.service;

import com.example.dictionary.entity.Word;

import java.util.List;
import java.util.Map;

public interface DictionaryService {
    Map<String,List<Word>> getWordsByTranslation(String translation);
    Integer updateOrCreateWord(Word word);

    Word getWordById(Integer wordId);

    void deleteWord(Integer wordId);

    Map<String,List<Word>> getAllWords();

    List<Word> getWordsByTranslationByType(String translation, String type);

    void deleteTranslationByWord(Integer translationId, Integer wordId);
    List<Word> getByWordValue(String value);

}
