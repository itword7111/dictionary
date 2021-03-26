package com.example.dictionary.controller;

import com.example.dictionary.Entity.Words;
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
public class DictionaryConsoleInterface implements com.example.dictionary.controller.DictionaryService {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/")
    public String getWord(Model model) {
        model.addAttribute("hashMap", dictionaryService.getAll());
        return "dictionaries";
    }

    @RequestMapping(value = "/{value}",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getWordByValue(@PathVariable("value") String value) {
        ModelAndView model = new ModelAndView("foundKeys");
        Map<String, List<Words>> dictionaries =dictionaryService.getWordByValue(value);
        if(dictionaries.size()==0)
            return null;
        model.getModelMap().addAttribute("keys", dictionaries);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWord(@PathVariable("id") Integer id) {
        dictionaryService.deleteByKey(id);
    }

    @RequestMapping(value = "createOrChange/", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    private Integer add(@RequestBody Words words) {
        return dictionaryService.add(words);
    }

    @RequestMapping(value = "createOrChange/{id}")
    private String createOrChange(@PathVariable("id") Integer id, Model model) {
        if (id == 0)
            model.addAttribute(new Words());
        else
            model.addAttribute(dictionaryService.get(id));
        return "addAndEdit";
    }

    @RequestMapping(value = "createOrChange/{id}", method = RequestMethod.DELETE, headers = "Content-Type=application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteValue(@PathVariable("id") Integer id, @RequestBody Integer wordId) {
        dictionaryService.deleteValueByKey(id, wordId);
    }

}
