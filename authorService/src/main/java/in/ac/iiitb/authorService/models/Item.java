package in.ac.iiitb.authorService.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer itemId;

    @Column(unique = true, nullable = false)
    protected String question;

    @Column(nullable = false)
    protected String itemType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="courseId", nullable=false)
    protected Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="authorId", nullable=false)
    protected Author author;

    public Item(Integer itemId) {
        this.itemId = itemId;
    }
}