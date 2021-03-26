package com.example.dictionary.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "value", schema = "dictionary2")
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "keys")
    @JsonIgnore
    private Collection<Words> words;

    public Value() {
    }

    public Value(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Collection<Words> getWords() {
        return words;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setWords(Collection<Words> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return value;
    }
}
