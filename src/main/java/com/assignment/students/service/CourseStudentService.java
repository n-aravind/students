package com.assignment.students.service;

import com.assignment.students.model.Student;
import com.assignment.students.repository.CourseStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseStudentService {

    private CourseStudentRepository courseStudentRepository;

    public CourseStudentService(CourseStudentRepository courseStudentRepository) {
        this.courseStudentRepository = courseStudentRepository;
    }

    public List<Long> getStudentIdsByCourseId(long courseId){
        return courseStudentRepository.getStudentIdsByCourseId(courseId);
    }

    public List<Student> getStudentsByCourseId(long courseId){
        return courseStudentRepository.getStudentsByCourseId(courseId);
    }

    public List<Long> getCourseIdsByStudentId(long studentId){
        return courseStudentRepository.getCourseIdsByStudentId(studentId);
    }

    public void unEnrollStudentFromCourse(long courseId,long studentId){
        courseStudentRepository.unEnrollStudentFromCourse(courseId,studentId);
    }

    public void enrollStudentIntoCourse(long courseId, long studentId) {
        courseStudentRepository.enrollStudentIntoCourse(courseId,studentId);
    }
}
