package com.iiitb.oaes.Bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "itemType", discriminatorType = DiscriminatorType.STRING)
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer itemId;

//    @Column(nullable = false)
//    private Integer questionId;

//    @Column(nullable = false)
//    private Integer version;

    // How many exams are using this question and evaluation of exam isn't finished
//    @Column(nullable = false)
//    private Integer usedCounter;

    @Column(unique = true, nullable = false)
    protected String question;

    // Discriminator column can be used a read-only property, so that's what this does
    @Column(insertable = false, updatable = false)
    private String itemType;

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

    //    public Items(Integer version, Integer usedCounter, String question, String option1, String option2, String option3, String option4, Integer answer) {
//        this.version = version;
//        this.usedCounter = usedCounter;
//        this.question = question;
//        this.option1 = option1;
//        this.option2 = option2;
//        this.option3 = option3;
//        this.option4 = option4;
//        this.answer = answer;
//    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    //    public Integer getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(Integer questionId) {
//        this.questionId = questionId;
//    }
//
//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
//
//    public Integer getUsedCounter() {
//        return usedCounter;
//    }
//
//    public void setUsedCounter(Integer usedCounter) {
//        this.usedCounter = usedCounter;
//    }

}
