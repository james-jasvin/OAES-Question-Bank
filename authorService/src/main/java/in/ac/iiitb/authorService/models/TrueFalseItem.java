package in.ac.iiitb.authorService.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@Entity
@Table(name = "true_false_items")
public class TrueFalseItem extends Item {
    @Column(nullable = false)
    private Boolean answer;

    // Same case as with MCQ, itemType must be set to TrueFalse
    public TrueFalseItem(Integer itemId) {
        this.itemId = itemId;
        this.itemType = "TrueFalse";
    }

    public TrueFalseItem(String question, Boolean answer, Author author, Course course) {
        this.question = question;
        this.answer = answer;
        this.itemType = "TrueFalse";

        this.author = author;
        this.course = course;
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
