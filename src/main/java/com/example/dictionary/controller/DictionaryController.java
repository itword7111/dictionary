package com.example.dictionary.controller;

import com.example.dictionary.entity.Word;
import com.example.dictionary.model.TypeOfDictionary;
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
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/")
    public ModelAndView getAllWords() {
        ModelAndView model = new ModelAndView("dictionaries");
        model.addObject("dictionaries", dictionaryService.getAllWords());
        return model;
    }

    @RequestMapping(value = "/",
            method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getWordsByTranslation(@RequestParam("translation") String translation, @RequestParam("type")  TypeOfDictionary type, @RequestParam("value") String value) {
        ModelAndView model = new ModelAndView("foundWordsByCondition");
        Map<TypeOfDictionary, List<Word>> dictionaries = dictionaryService.getWordsByTranslation(translation,type,value);
        if (dictionaries.size() == 0)
            return null;
        model.getModelMap().addAttribute("dictionaries", dictionaries);
        return model;
    }

//    @RequestMapping(value = "/", method = RequestMethod.DELETE, headers = "Content-Type=application/json")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteWord(@RequestParam(value = "id", required = false) Integer id ) {
//
//        dictionaryService.deleteWord(id);
//    }
    @RequestMapping(value = "deleteWord/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWord(@RequestParam("id") Integer id) {
        dictionaryService.deleteWord(id);
    }

    @RequestMapping(value = "createOrChange/", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    private Integer updateOrCreateWord(@RequestBody Word words) {
        return dictionaryService.updateOrCreateWord(words);
    }

    @RequestMapping(value = "createOrChange/", method = RequestMethod.GET)
    private ModelAndView getWordById(@RequestParam("id") Integer id) {
        ModelAndView model = new ModelAndView("addAndEdit");
        if (id == null) {
            Word newWord = new Word();
            model.addObject("word",newWord);
        } else {
            model.addObject("word",dictionaryService.getWordById(id));
        }
        return model;
    }

    @RequestMapping(value = "createOrChange/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTranslationByWord(@RequestParam("id") Integer id, @RequestParam("wordId")  Integer wordId) {
        dictionaryService.deleteTranslationByWord(id, wordId);
    }

}
