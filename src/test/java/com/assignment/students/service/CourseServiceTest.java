package com.assignment.students.service;

import com.assignment.students.model.Course;
import com.assignment.students.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// GP research TDD and the do it
class CourseServiceTest {

    private CourseRepository courseRepository = mock(CourseRepository.class);
    private CourseService courseService = new CourseService(courseRepository);

    @Test
    void addClass() {
        Course subject = Course.builder().courseId(1).courseName("Biology").courseDescription("Test Description").build();
        courseService.addCourse(subject);
        Mockito.when(courseRepository.addCourse(notNull())).thenReturn(1L);
        ArgumentCaptor<Course> classArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository,times(1)).addCourse(subject);
        verify(courseRepository).addCourse(classArgumentCaptor.capture());
        assertEquals("Biology",classArgumentCaptor.getValue().getCourseName());
        assertEquals(1, courseRepository.addCourse(subject));
    }

    @Test
    void getAllClasses() {
        Course subject1 = Course.builder().courseName("Biology").courseDescription("Test Description").build();
        Course subject2 = Course.builder().courseName("Algebra").courseDescription("Test Description").build();
        List<Course> courseList = new ArrayList<>();
        courseList.add(subject1);
        courseList.add(subject2);
        when(courseService.getAllCourses()).thenReturn(courseList);
        assertEquals(2, courseService.getAllCourses().size());
        verify(courseRepository,times(1)).getAllCourses();
    }

    @Test
    void getClassById() {
        when(courseService.getCourseById(1)).thenReturn(Course.builder().courseName("History").courseDescription("Test Description").build());
        Course subject = courseService.getCourseById(1);
        assertEquals("History",subject.getCourseName());
        assertEquals("Test Description",subject.getCourseDescription());
    }

    @Test
    void updateClass() {
        Course subject = Course.builder().courseId(1).courseName("Algebra").courseDescription("Test Description").build();
        courseService.addCourse(subject);
        subject.setCourseDescription("Changed Description");
        courseService.updateCourse(1,subject);
        verify(courseRepository,times(1)).updateCourse(1,subject);
    }

    @Test
    void deleteClass() {
        courseService.deleteCourse(1);
        verify(courseRepository,times(1)).deleteCourse(1);
    }

    @Test
    public void dataIntegrityViolationException(){
        doThrow(DataIntegrityViolationException.class)
                .when(courseRepository)
                .addCourse(Course.builder().build());
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> courseService.addCourse(Course.builder().build()));
    }

    @Test
    void addClasses() {
        Course subject1 = Course.builder().courseName("Biology").courseDescription("Test Description").build();
        Course subject2 = Course.builder().courseName("Algebra").courseDescription("Test Description").build();
        List<Course> courseList = new ArrayList<>();
        courseList.add(subject1);
        courseList.add(subject2);
        courseService.addCourses(courseList);
        verify(courseRepository,times(1)).addCourse(subject1);
        verify(courseRepository,times(1)).addCourse(subject2);
    }
}