package in.ac.iiitb.itemService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.core.MediaType;

import in.ac.iiitb.itemService.models.Course;
import in.ac.iiitb.itemService.service.CourseService;

@RestController
// @CrossOrigin
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON
    )
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courseList = courseService.getCourses();
        if (courseList == null)
            return new ResponseEntity<List<Course>>(courseList, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
    }
}