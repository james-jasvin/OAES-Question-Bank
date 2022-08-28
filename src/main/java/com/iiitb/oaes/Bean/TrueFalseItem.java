package com.iiitb.oaes.Bean;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("TrueFalse")
@Check(constraints = "answer BETWEEN 1 AND 4")
public class TrueFalseItem extends Item implements Serializable {
    @Column
    private Boolean answer;

    public TrueFalseItem() { }

    public TrueFalseItem(Integer itemId) { this.itemId = itemId; }

    public TrueFalseItem(String question, Boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "\nQuestion ID: " + itemId + '\n' +
                "Question: " + question + '\n' +
                "Correct Answer: " + answer + '\n' +
                "Course: " + course.getName() + '\n';
    }
}
