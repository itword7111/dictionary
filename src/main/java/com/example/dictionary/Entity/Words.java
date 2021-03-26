package com.example.dictionary.Entity;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "words", schema = "dictionary2")
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value")
    private String value;
    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(schema = "dictionary2", name = "keys", joinColumns = @JoinColumn(name = "keyId"), inverseJoinColumns = @JoinColumn(name = "valueId"))

    private Collection<Value> keys;

    public Words() {

    }

    public Words(String value, String type, Collection<Value> keys) {
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

    public void setKeys(Collection<Value> keys) {
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

    public Collection<Value> getKeys() {
        return keys;
    }

    @Override
    public String toString() {
        String ans = value + " ";
//            ans+=": ";
//            for (Value s : keys
//            ) {
//                ans += s.getValue() + " ";
//            }
        return ans;
    }
}