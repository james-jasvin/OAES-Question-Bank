package com.iiitb.oaes.Bean;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Check(constraints = "answer BETWEEN 1 AND 4")
public class Items implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

//    @Column(nullable = false)
//    private Integer questionId;

//    @Column(nullable = false)
//    private Integer version;

    // How many exams are using this question and evaluation of exam isn't finished
//    @Column(nullable = false)
//    private Integer usedCounter;

    @Column(unique = true, nullable = false)
    private String question;

    @Column(nullable = false)
    private String option1;
    @Column(nullable = false)
    private String option2;
    @Column(nullable = false)
    private String option3;
    @Column(nullable = false)
    private String option4;

    @Column(nullable = false)
    private Integer answer;

    @ManyToOne
    @JoinColumn(name="authorId", nullable=false)
    private Authors author;

    public Items() {

    }

    public Items(Integer itemId) {
        this.itemId = itemId;
    }

    public Items(String question, String option1, String option2, String option3, String option4, Integer answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
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

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Question:" + question + '\n' +
                "Option 1:" + option1 + '\n' +
                "Option 2:" + option2 + '\n' +
                "Option 3:" + option3 + '\n' +
                "Option 4:" + option4 + '\n' +
                "Correct Option:" + answer + "\n";
    }
}
