package com.iiitb.oaes.Bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer itemId;

    @Column(unique = true, nullable = false)
    protected String question;

    @Column(nullable = false)
    protected String itemType;

    @ManyToOne
    @JoinColumn(name="courseId", nullable=false)
    protected Course course;

    @ManyToOne
    @JoinColumn(name="authorId", nullable=false)
    protected Author author;

    public Item() {
    }

    public Item(Integer itemId) {
        this.itemId = itemId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}