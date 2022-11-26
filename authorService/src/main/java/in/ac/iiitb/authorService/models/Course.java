package in.ac.iiitb.authorService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import in.ac.iiitb.authorService.models.Course;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String abbreviation;

    // @JsonIgnore helps to omit "items" field from serialization of Course object
    // That is, when Course object is converted DB object to Java object
    @OneToMany(mappedBy="course")
    @JsonIgnore
    private List<Item> items;

    public Course(String name, String abbreviation, List<Item> items) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.items = items;
    }

    @Override
    public String toString() {
        return "Course { " +
                "Id:" + courseId +
                ", Name:" + name +
                ", Abbreviation:" + abbreviation +
                " }";
    }
}
