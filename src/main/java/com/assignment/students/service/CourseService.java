package com.assignment.students.service;

import com.assignment.students.model.Course;
import com.assignment.students.repository.CourseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(Course subject) {
        long id = courseRepository.addCourse(subject);
        subject.setCourseId(id);
        return subject;
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public Course getCourseById(long id) {
        return courseRepository.getCourseById(id);
    }

    @Transactional
    public Course updateCourse(long id, Course subject) {
        courseRepository.updateCourse(id, subject);
        subject.setCourseId(id);
        return subject;
    }

    public void deleteCourse(long id) {
        courseRepository.deleteCourse(id);
    }

    @Transactional
    public List<Course> addCourses(List<Course> subjectList) {
        for (Course subject : subjectList) {
            long id = courseRepository.addCourse(subject);
            subject.setCourseId(id);
        }
        return subjectList;
    }

}
