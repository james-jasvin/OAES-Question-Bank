package in.ac.iiitb.authorService.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@Entity
@Table(name = "mcq_items")
// Check constraint for ensuring that Correct Answer field "answer" only contains valid MCQ options, i.e. 1-4
@Check(constraints = "answer BETWEEN 1 AND 4")
public class MCQItem extends Item {
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

    // itemType must be initialized to MCQ by default to ensure that the Object type is correctly interpreted
    // by the Services => FasterXML.Jackson
    public MCQItem(Integer itemId) {
        this.itemId = itemId;
        this.itemType = "MCQ";
    }

    public MCQItem(Integer itemId, String question, String option1, String option2, String option3, String option4, Integer answer, Author author, Course course) {
        this.itemId = itemId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.itemType = "MCQ";

        this.author = author;
        this.course = course;
    }

    @Override
    public String toString() {
        return "\nQuestion ID:" + itemId + '\n' +
                "Question: " + question + '\n' +
                "Option 1: " + option1 + '\n' +
                "Option 2: " + option2 + '\n' +
                "Option 3: " + option3 + '\n' +
                "Option 4: " + option4 + '\n' +
                "Correct Option: " + answer + '\n' +
                // To handle annoying NULL pointer exception errors
                (course != null? "Course: " + course.getName() + '\n': "");
    }
}