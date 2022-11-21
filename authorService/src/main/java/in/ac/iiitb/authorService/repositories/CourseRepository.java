package in.ac.iiitb.authorService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ac.iiitb.authorService.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
