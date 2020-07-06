package com.assignment.students.controller;

import com.assignment.students.model.Course;
import com.assignment.students.model.Student;
import com.assignment.students.service.CourseService;
import com.assignment.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class WebController {

    private final CourseService courseService;
    private final StudentService studentService;

    public WebController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/v1/students")
    public String studentForm(Model model){
        model.addAttribute("student",new Student());
        return"student-form";
    }

    @GetMapping("/v1/courses")
    public String courseForm(Model model){
        model.addAttribute("course",new Course());
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-form";
    }


    @GetMapping("/v1/courses/{id}/delete")
    public String courseForm(@PathVariable long id,  Model model){
        courseService.deleteCourse(id);
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-registry";
    }

    @GetMapping("/v1/courses/{id}")
    public String updateCourse(@PathVariable long id, Model model){
        Course subject = courseService.getCourseById(id);
        model.addAttribute("course",subject);
        return "update-course";
    }

    @PostMapping(path = "/v1/courses",params="action=save")
    public String courseSubmit(@Valid Course subject, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "course-form";
        }

        courseService.addCourse(subject);
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-registry";
    }

    @PostMapping(path = "/v1/courses",params="action=cancel")
    public String cancelFormSubmission(@Valid Course subject, BindingResult result, Model model){
        return "course-registry";
    }

    @PostMapping(path = "/v1/courses/{id}")
    public String updateCourse(@PathVariable long id, @Valid Course subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "course";
        }
        courseService.updateCourse(id, subject);
        model.addAttribute("coursees", courseService.getAllCourses());
        return "course-registry";
    }

    @GetMapping("/v1/courses/all")
    public String getAllCourses(Model model){
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-registry";
    }

    @GetMapping("/v1/students/all")
    public String getAllStudents(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }

    @GetMapping("/v1/students/{studentId}")
    public String getStudent(@PathVariable long studentId,  Model model){
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student",student);
        return "student-form";
    }

    @GetMapping("/v1/students/{studentId}/delete")
    public String getStudent(@PathVariable String studentId,  Model model){
        studentService.deleteStudent(studentId);
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }

    @PostMapping("/v1/students")
    public String studentSubmit(@Valid Student student, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "student";
        }
        studentService.addStudent(student);
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }
}
