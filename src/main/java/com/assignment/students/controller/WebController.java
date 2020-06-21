package com.assignment.students.controller;

import com.assignment.students.model.Class;
import com.assignment.students.model.Student;
import com.assignment.students.service.ClassService;
import com.assignment.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class WebController {

    private final ClassService classService;
    private final StudentService studentService;

    public WebController(ClassService classService, StudentService studentService) {
        this.classService = classService;
        this.studentService = studentService;
    }

    @GetMapping("/v1/students")
    public String studentForm(Model model){
        model.addAttribute("students",new Student());
        return"students";
    }

    @GetMapping("/v1/classes")
    public String courseForm(Model model){
        model.addAttribute("class",new Class());
        model.addAttribute("classes", classService.getAllClasses());
        return "course-form";
    }


    @GetMapping("/v1/classes/{id}/delete")
    public String courseForm(@PathVariable long id,  Model model){
        classService.deleteClass(id);
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @GetMapping("/v1/classes/{id}")
    public String updateClass(@PathVariable long id, Model model){
        Class subject = classService.getClassById(id);
        model.addAttribute("class",subject);
        return "update-class";
    }

    @PostMapping(path = "/v1/classes",params="action=save")
    public String classSubmit(@Valid Class subject, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "course-form";
        }

        classService.addClass(subject);
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @PostMapping(path = "/v1/classes",params="action=cancel")
    public String cancelFormSubmission(@Valid Class subject, BindingResult result, Model model){
        return "class-registry";
    }

    @PostMapping(path = "/v1/classes/{id}")
    public String updateClass(@PathVariable long id, @Valid Class subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "class";
        }
        classService.updateClass(id, subject);
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @GetMapping("/v1/classes/all")
    public String getAllClasses(Model model){
        model.addAttribute("classes",classService.getAllClasses());
        return "class-registry";
    }

    @GetMapping("/v1/students/all")
    public String getAllStudents(Model model){
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
