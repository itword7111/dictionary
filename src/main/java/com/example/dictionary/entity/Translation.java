package com.example.dictionary.entity;

import javax.persistence.*;

@Entity
@Table(name = "translations", schema = "dictionary")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    public Translation() {
    }

    public Translation(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
