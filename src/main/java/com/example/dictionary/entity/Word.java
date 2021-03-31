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
    private Collection<Translation> keys;

    public Word() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        for (TypeOfDictionary typeOfDictionary : TypeOfDictionary.values()
        ) {
            if (typeOfDictionary.toString().equals(type)) {
                this.type = typeOfDictionary;
                return;
            }
        }
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
        return type.toString();
    }

    public TypeOfDictionary getTypeEnum() {
        return type;
    }

    public Collection<Translation> getKeys() {
        return keys;
    }

}