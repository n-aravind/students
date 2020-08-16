package com.assignment.students.service;

import com.assignment.students.model.Course;
import com.assignment.students.model.EmailAddresses;
import com.assignment.students.model.EmailType;
import com.assignment.students.model.Student;
import com.assignment.students.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepository = mock(StudentRepository.class);
    private StudentService studentService = new StudentService(studentRepository);

    @Test
    void addStudent() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("Algebra").courseDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).courses(courseList).build();
        studentService.addStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).addStudent(studentArgumentCaptor.capture());
        assertEquals("Algebra", studentArgumentCaptor.getValue().getCourses().get(0).getCourseName());
        assertEquals(EmailType.PERSONAL, studentArgumentCaptor.getValue().getEmailAddresses().get(0).getEmailType());
    }

    @Test
    void getAllStudents() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("Algebra").courseDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).courses(courseList).build();
        studentService.addStudent(student);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        when(studentService.getAllStudents()).thenReturn(studentList);
        assertEquals(1, studentService.getAllStudents().size());
        verify(studentRepository, times(1)).getAllStudents();
    }

    @Test
    void enrollStudent() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseId(1).courseName("Algebra").courseDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).courses(courseList).build();
        studentService.addStudent(student);
        courseList.add(Course.builder().courseId(2).courseName("History").courseDescription("Test Description").build());
        student.setCourses(courseList);
        studentService.enrollStudent(student);
        verify(studentRepository,times(1)).enrollStudent(1L,0L);
        verify(studentRepository,times(1)).enrollStudent(2L,0L);
    }

    @Test
    void unEnrollStudent() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseId(1).courseName("Algebra").courseDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).courses(courseList).build();
        studentService.addStudent(student);
        student.setCourses(courseList);
        studentService.unEnrollStudent(student);
        verify(studentRepository,times(1)).unEnrollStudent(1L,0L);
    }

    @Test
    void deleteStudent() {
        studentService.deleteStudent("1");
        verify(studentRepository, times(1)).deleteStudent("1");
    }

    @Test
    void getStudentById() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("Algebra").courseDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        when(studentService.getStudentById(1)).thenReturn(Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).courses(courseList).build());
        Student student = studentService.getStudentById(1);
        assertEquals("John", student.getFirstName());
    }
}