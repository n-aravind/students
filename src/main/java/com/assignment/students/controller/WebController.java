package com.assignment.students.controller;

import com.assignment.students.model.Class;
import com.assignment.students.model.Student;
import com.assignment.students.repository.ClassRepository;
import com.assignment.students.service.ClassService;
import com.assignment.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class WebController {

    // TODO GP need final keyword
    private ClassService classService;
    // TODO GP need final keyword
    private StudentService studentService;

    public WebController(ClassService classService, StudentService studentService) {
        this.classService = classService;
        this.studentService = studentService;
    }

    // TODO GP /students. Even better would be /v1/students
    @GetMapping("/student")
    public String studentForm(Model model){
        model.addAttribute("student",new Student());
        // TODO GP singular or plural?
        return"student";
    }

    // TODO GP /v1/classes
    @GetMapping("/class")
    public String courseForm(Model model){
        model.addAttribute("class",new Class());
        return "class";
    }

    @GetMapping("delete/class/{id}")
    public String courseForm(@PathVariable long id,  Model model){
        classService.deleteClass(id);
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @GetMapping("/class/{id}")
    public String updateClass(@PathVariable long id, Model model){
        Class subject = classService.getClassById(id);
        model.addAttribute("class",subject);
        return "update-class";
    }

    @PostMapping("/class")
    public String classSubmit(@Valid Class subject, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "class";
        }

        classService.addClass(subject);
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @PostMapping(path = "/class/{id}")
    public String updateClass(@PathVariable long id, @Valid Class subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "class";
        }
        classService.updateClass(id, subject);
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @GetMapping("/classes")
    public String getAllClasses(Model model){
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @GetMapping("/students")
    public String getAllStudents(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }


    @PostMapping("/student")
    public String studentSubmit(@Valid Student student, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "student";
        }
        studentService.addStudent(student);
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }
}
