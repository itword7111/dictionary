package com.example.dictionary.service;

import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface DictionaryService {
    Map<TypeOfDictionary, List<Word>> getAllWords();

    Map<TypeOfDictionary, List<Word>> getWordsByTranslation(String translation, TypeOfDictionary type, String wordValue);

    Integer updateOrCreateWord(Word word);

    Word getWordById(Integer wordId);

    void deleteWord(Integer wordId);

    void deleteTranslationByWord(Integer translationId, Integer wordId);

}
