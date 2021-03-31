package com.example.dictionary.validation;


import com.example.dictionary.model.TypeOfDictionary;

public interface DictionaryValidator {
    boolean validation(String key, TypeOfDictionary typeOfDictionary);
}
