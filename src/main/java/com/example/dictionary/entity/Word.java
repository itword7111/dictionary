package com.example.dictionary.entity;


import com.example.dictionary.model.TypeOfDictionary;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "words", schema = "dictionary")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeOfDictionary type;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "word_id")
    private Collection<Translation> translations;

    public Word() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TypeOfDictionary getType() {
        return type;
    }

    public void setType(TypeOfDictionary type) {
        this.type = type;
    }

    public Collection<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Collection<Translation> translations) {
        this.translations = translations;
    }
}