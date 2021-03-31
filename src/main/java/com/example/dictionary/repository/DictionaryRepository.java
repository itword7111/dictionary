package com.example.dictionary.repository;

import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;

import java.util.List;

public interface DictionaryRepository {
    List<Word> getWordsByTranslation(String translation);
    Word getWordById(Integer wordId);

    List<Word> getAllWords();

    Integer updateOrCreateWord(Word words);

    List<Word> getWordsByTranslationByType(String translation, TypeOfDictionary type);

    void deleteWord(Integer wordId);
    void deleteTranslationByWord(Integer translationId, Integer wordId);
    List<Word> getByWordValue(String value);
}
