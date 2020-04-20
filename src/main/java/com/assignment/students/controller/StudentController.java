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

    @PostMapping(path = "student")
    public void addStudent(@RequestBody Student student){
        studentService.addStudent(student);
    }

    @GetMapping(path = "student")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PatchMapping(path = "student")
    public void enrollStudent(@RequestBody Student student, @RequestParam String action){
        // GP instead of action use content-type
        if(action.equalsIgnoreCase("enroll")){
            // 'Content-Type': 'application/vnd.aravind.enroll+json'
            studentService.enrollStudent(student);
        }else if(action.equalsIgnoreCase("unenroll")){
            // 'Content-Type': 'application/vnd.aravind.unenroll+json'
            studentService.unEnrollStudent(student);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Action Not Supported");
        }
    }

    @DeleteMapping(path = "student/{id}")
    public void deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
    }

    @GetMapping(path = "student/{id}")
    public Student getStudentById(@PathVariable long id){
        return studentService.getStudentById(id);
    }
}
