package com.assignment.students.service;

import com.assignment.students.model.Class;
import com.assignment.students.model.Student;
import com.assignment.students.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student addStudent(Student student) {
        long id = studentRepository.addStudent(student);
        student.setStudentId(id);
        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @Transactional
    public Student enrollStudent(Student student) {
        for (Class subject : student.getClasses()) {
            studentRepository.enrollStudent(subject.getClassId(), student.getStudentId());
        }
        return student;
    }

    @Transactional
    public Student unEnrollStudent(Student student) {
        for (Class subject : student.getClasses()) {
            studentRepository.unEnrollStudent(subject.getClassId(), student.getStudentId());
        }
        return student;
    }

    @Transactional
    public void deleteStudent(String id) {
        studentRepository.deleteStudent(id);
    }

    public Student getStudentById(long id) {
        return studentRepository.getStudentById(id);
    }
}
