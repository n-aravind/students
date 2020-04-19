package com.assignment.students.controller;

import com.assignment.students.service.ClassService;
import com.assignment.students.model.Class;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ClassController {

    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping(path="class")
    public void addClass(@RequestBody Class subject){
        try {
            classService.addClass(subject);
        }catch (DuplicateKeyException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Class With this name already exists", e);
        }
    }

    @PostMapping(path="class/batch")
    public void addClass(@RequestBody List<Class> subjectList){
        try {
            classService.addClasses(subjectList);
        }catch (DuplicateKeyException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Class With this name already exists", e);
        }
    }

    @GetMapping(path="class")
    public List<Class> getAllClasses(){
        return classService.getAllClasses();
    }

    @GetMapping(path = "class/{id}")
    public Class getClassById(@PathVariable long id){
        return classService.getClassById(id);
    }

    @PutMapping(path = "class")
    public void updateClass(@RequestBody Class subject){
        classService.updateClass(subject);
    }

    @DeleteMapping(path = "class/{name}")
    public void deleteClass(@PathVariable String name){
        try{
            classService.deleteClass(name);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot delete class when students are enrolled in it",e);
        }
    }

}