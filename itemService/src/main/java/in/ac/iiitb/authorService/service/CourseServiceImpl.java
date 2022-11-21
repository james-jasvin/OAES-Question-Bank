package in.ac.iiitb.authorService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ac.iiitb.authorService.models.Course;
import in.ac.iiitb.authorService.repositories.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    /*
        Create a Course object in the Database
        Return the created object
    */
    @Override
    public Course registerCourse(Course course) {
        return courseRepository.save(course);
    } 

    /*
        Return all the Courses in the Database
    */
    @Override
    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    /*
        Return the Course with the specified Id in the Database
        Return NULL if no Course with specified Id found
    */
    @Override
    public Course getCourseById(Integer courseId) {
        Optional<Course> selectedCourse = courseRepository.findById(courseId);

        // If no course with given id exists, then return null
        if (!selectedCourse.isPresent())
            return null;

        return selectedCourse.get();
    }
}