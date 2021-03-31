package com.example.dictionary.validation;


import com.example.dictionary.model.TypeOfDictionary;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Component
public class DictionaryValidatorImpl implements DictionaryValidator {

    private static final Map<TypeOfDictionary, String> regexMap;

    static {
        Map<TypeOfDictionary, String> regexMapInit = new HashMap<>();
        regexMapInit.put(TypeOfDictionary.latin4, "[A-Za-z]{4}");
        regexMapInit.put(TypeOfDictionary.arab5, "[0-9]{5}");
        regexMap = Collections.unmodifiableMap(regexMapInit);
    }

    public boolean validation(String key, TypeOfDictionary typeOfDictionary) {
        return key.matches(regexMap.get(typeOfDictionary));
    }
}
