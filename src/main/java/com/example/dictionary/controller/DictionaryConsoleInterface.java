package com.example.dictionary.controller;

import com.example.dictionary.entity.Word;
import com.example.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dictionary")
public class DictionaryConsoleInterface {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/")
    public String getAllWords(Model model) {
        model.addAttribute("dictionaries", dictionaryService.getAllWords());
        return "dictionaries";
    }

    @RequestMapping(value = "/{value}",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getWordsByTranslation(@PathVariable("value") String value) {
        ModelAndView model = new ModelAndView("foundWordsByCondition");
        Map<String, List<Word>> dictionaries = dictionaryService.getWordsByTranslation(value);
        if (dictionaries.size() == 0)
            return null;
        model.getModelMap().addAttribute("dictionaries", dictionaries);
        return model;
    }

    @RequestMapping(value = "/{value}",
            method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getWordsByTranslationByType(@PathVariable("value") String translation, @RequestBody String type) {
        ModelAndView model = new ModelAndView("foundWordsByConditionByType");
        List<Word> words = dictionaryService.getWordsByTranslationByType(translation, type);
        if (words.size() == 0)
            return null;
        model.getModelMap().addAttribute("dictionaries", words);
        return model;
    }

    @RequestMapping(value = "/getByWordValue/{value}",
            method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getByWordValue(@PathVariable("value") String value) {
        ModelAndView model = new ModelAndView("foundWordsByConditionByType");
        List<Word> words = dictionaryService.getByWordValue(value);
        if (words.size() == 0)
            return null;
        model.getModelMap().addAttribute("dictionaries", words);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWord(@PathVariable("id") Integer id) {
        dictionaryService.deleteWord(id);
    }

    @RequestMapping(value = "createOrChange/", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    private Integer updateOrCreateWord(@RequestBody Word words) {
        if (words.getId() == 0)
            words.setId(null);
        return dictionaryService.updateOrCreateWord(words);
    }

    @RequestMapping(value = "createOrChange/{id}")
    private String getWordById(@PathVariable("id") Integer id, Model model) {
        if (id == 0) {
            Word newWord = new Word();
            newWord.setId(0);
            model.addAttribute(newWord);
        } else {
            model.addAttribute(dictionaryService.getWordById(id));
        }
        return "addAndEdit";
    }

    @RequestMapping(value = "createOrChange/{id}", method = RequestMethod.DELETE, headers = "Content-Type=application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTranslationByWord(@PathVariable("id") Integer id, @RequestBody Integer wordId) {
        dictionaryService.deleteTranslationByWord(id, wordId);
    }

}
