package com.example.dictionary.repository;

import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;

import java.util.List;

public interface DictionaryRepository {
    List<Word> getAllWords();

    List<Word> getWordsByTranslation(String translation, TypeOfDictionary type, String wordValue);

    Word getWordById(Integer wordId);

    Integer updateOrCreateWord(Word word);

    void deleteWord(Integer wordId);

    void deleteTranslationByWord(Integer translationId, Integer wordId);

}
