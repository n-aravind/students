package com.assignment.students.controller;

import com.assignment.students.model.Class;
import com.assignment.students.service.ClassService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassController {

    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping(path = "/api/v1/classes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Class addClass(@RequestBody Class subject) {
        return classService.addClass(subject);
    }

    @PostMapping(path = "/api/v1/classes", consumes = "application/vnd.classes.batch+json")
    public List<Class> addClass(@RequestBody List<Class> subjectList) {
        return classService.addClasses(subjectList);
    }

    @GetMapping(path = "/api/v1/classes")
    public List<Class> getAllClasses() {
        return classService.getAllClasses();
    }

    @GetMapping(path = "/api/v1/classes/{id}")
    public Class getClassById(@PathVariable long id) {
        return classService.getClassById(id);
    }

    @PutMapping(path = "/api/v1/classes/{id}")
    public Class updateClass(@PathVariable long id, @RequestBody Class subject) {
        return classService.updateClass(id, subject);
    }

    @DeleteMapping(path = "/api/v1/classes/{id}")
    public void deleteClass(@PathVariable long id) {
        classService.deleteClass(id);
    }

}
