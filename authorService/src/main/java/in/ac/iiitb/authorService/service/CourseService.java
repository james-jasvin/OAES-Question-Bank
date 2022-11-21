package in.ac.iiitb.authorService.service;

import java.util.List;

import in.ac.iiitb.authorService.models.Course;

public interface CourseService {
  Course registerCourse(Course course);
  List<Course> getCourses();
  Course getCourseById(Integer courseId);
}
