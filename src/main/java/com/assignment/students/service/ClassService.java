package com.assignment.students.service;

import com.assignment.students.model.Class;
import com.assignment.students.repository.ClassRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClassService {
    private ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void addClass(Class subject) {
        classRepository.addClass(subject);
    }

    public List<Class> getAllClasses() {
        return classRepository.getAllClasses();
    }

    public Class getClassById(long id) {
        return classRepository.getClassById(id);
    }

    public void updateClass(Class subject) {
        classRepository.updateClass(subject);
    }

    public void deleteClass(String name) {
        classRepository.deleteClass(name);
    }

    @Transactional
    public void addClasses(List<Class> subjectList) {
        for(Class subject : subjectList){
            classRepository.addClass(subject);
        }
    }
}
