package in.ac.iiitb.itemService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ac.iiitb.itemService.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
