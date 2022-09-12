package com.iiitb.oaes.Bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String abbreviation;

    @OneToMany(mappedBy="course")
    @JsonIgnore // to omit from serialization
    private List<Item> items;

    public Course(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public Course() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Course { " +
                "Id:" + courseId +
                ", Name:" + name +
                ", Abbreviation:" + abbreviation +
                " }" + items;
    }
}
