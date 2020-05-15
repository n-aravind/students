package com.assignment.students.controller;

import com.assignment.students.service.StudentService;
import com.assignment.students.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "api/v1/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping(path = "api/v1/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PatchMapping(path = "api/v1/students", consumes = "application/vnd.aravind.enroll+json")
    public Student enrollStudent(@RequestBody Student student) {
        return studentService.enrollStudent(student);
    }

    @PatchMapping(path = "api/v1/students", consumes = "application/vnd.aravind.unenroll+json")
    public Student unEnrollStudent(@RequestBody Student student) {
        return studentService.unEnrollStudent(student);
    }

    @DeleteMapping(path = "api/v1/students/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

    @GetMapping(path = "api/v1/students/{id}")
    public Student getStudentById(@PathVariable long id) {
        return studentService.getStudentById(id);
    }
}
