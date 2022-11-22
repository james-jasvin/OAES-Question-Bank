package in.ac.iiitb.itemService.service;

import java.util.List;

import in.ac.iiitb.itemService.models.Course;

public interface CourseService {
  Course registerCourse(Course course);
  List<Course> getCourses();
  Course getCourseById(Integer courseId);
}
