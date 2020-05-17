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

    public Class addClass(Class subject) {
        long id = classRepository.addClass(subject);
        subject.setClassId(id);
        return subject;
    }

    public List<Class> getAllClasses() {
        return classRepository.getAllClasses();
    }

    public Class getClassById(long id) {
        return classRepository.getClassById(id);
    }

    @Transactional
    public Class updateClass(long id,Class subject) {
        classRepository.updateClass(id, subject);
        subject.setClassId(id);
        return subject;
    }

    public void deleteClass(long id) {
        classRepository.deleteClass(id);
    }

    @Transactional
    public List<Class> addClasses(List<Class> subjectList) {
        for (Class subject : subjectList) {
            long id = classRepository.addClass(subject);
            subject.setClassId(id);
        }
        return subjectList;
    }
}
