package com.assignment.students.service;

import com.assignment.students.model.Student;
import com.assignment.students.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addStudent(Student student) {
        studentRepository.addStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public void enrollStudent(Student student) {
        studentRepository.enrollStudent(student);
    }

    public void unEnrollStudent(Student student) {
        studentRepository.unEnrollStudent(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteStudent(id);
    }

    public Student getStudentById(long id) {
        return studentRepository.getStudentById(id);
    }
}
