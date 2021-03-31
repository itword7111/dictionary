package com.example.dictionary.Entity;


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
    private String type;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn( name = "word_id")
    private Collection<Translation> keys;

    public Word() {

    }

    public Word(String value, String type, Collection<Translation> keys) {
        this.value = value;
        this.type = type;
        this.keys = keys;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKeys(Collection<Translation> keys) {
        this.keys = keys;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public Collection<Translation> getKeys() {
        return keys;
    }

}