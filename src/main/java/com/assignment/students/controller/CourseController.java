package com.assignment.students.controller;

import com.assignment.students.model.Course;
import com.assignment.students.service.CourseService;
import com.assignment.students.service.CourseStudentService;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    private CourseService courseService;
    private CourseStudentService courseStudentService;

    public CourseController(CourseService courseService, CourseStudentService courseStudentService) {
        this.courseService = courseService;
        this.courseStudentService = courseStudentService;
    }

    @PostMapping(path = "/api/v1/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Course addCourse(@RequestBody Course subject) {
        return courseService.addCourse(subject);
    }

    @PostMapping(path = "/api/v1/courses", consumes = "application/vnd.classes.batch+json")
    public List<Course> addCourse(@RequestBody List<Course> subjectList) {
        return courseService.addCourses(subjectList);
    }

    @GetMapping(path = "/api/v1/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/api/v1/courses/{id}")
    public Course getCourseById(@PathVariable long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping(path = "/api/v1/courses/{id}")
    public Course updateCourse(@PathVariable long id, @RequestBody Course subject) {
        return courseService.updateCourse(id, subject);
    }

    @DeleteMapping(path = "/api/v1/courses/{id}")
    public void deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/v1/courses/studentsenrolled/{id}")
    public List<Long> getStudentsByCourseId(@PathVariable long id){
        return courseStudentService.getStudentIdsByCourseId(id);
    }

}
