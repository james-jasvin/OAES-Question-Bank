package com.iiitb.oaes.Bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TrueFalseItem extends Item implements Serializable {
    @Column(nullable = false)
    private Boolean answer;

    public TrueFalseItem() { }

    // Same case as with MCQ, itemType must be set to TrueFalse
    public TrueFalseItem(Integer itemId) {
        this.itemId = itemId;
        this.itemType = "TrueFalse";
    }

    public TrueFalseItem(String question, Boolean answer) {
        this.question = question;
        this.answer = answer;
        this.itemType = "TrueFalse";
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
                // To handle annoying NULL pointer exception errors
                (course != null? "Course: " + course.getName() + '\n': "");
    }
}
