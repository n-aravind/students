package com.assignment.students.service;

import com.assignment.students.model.Class;
import com.assignment.students.model.EmailAddresses;
import com.assignment.students.model.Student;
import com.assignment.students.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepository = mock(StudentRepository.class);
    private StudentService studentService = new StudentService(studentRepository);

    @Test
    void addStudent() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType("personal").build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).addStudent(studentArgumentCaptor.capture());
        assertEquals("Algebra",studentArgumentCaptor.getValue().getClasses().get(0).getClassName());
        assertEquals("personal",studentArgumentCaptor.getValue().getEmailAddresses().get(0).getEmailType());
    }

    @Test
    void getAllStudents() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType("personal").build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        when(studentService.getAllStudents()).thenReturn(studentList);
        assertEquals(1,studentService.getAllStudents().size());
        verify(studentRepository,times(1)).getAllStudents();
    }

    @Test
    void enrollStudent() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType("personal").build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        classList.add(Class.builder().className("History").classDescription("Test Description").build());
        student.setClasses(classList);
        studentService.enrollStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).enrollStudent(studentArgumentCaptor.capture());
        assertEquals(2,studentArgumentCaptor.getValue().getClasses().size());
    }

    @Test
    void unEnrollStudent() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType("personal").build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        classList.clear();
        student.setClasses(classList);
        studentService.unEnrollStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).unEnrollStudent(studentArgumentCaptor.capture());
        assertEquals(0,studentArgumentCaptor.getValue().getClasses().size());
    }

    @Test
    void deleteStudent() {
        studentService.deleteStudent("1");
        verify(studentRepository,times(1)).deleteStudent("1");
    }

    @Test
    void getStudentById() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType("personal").build());
        when(studentService.getStudentById(1)).thenReturn(Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build());
        Student student = studentService.getStudentById(1);
        assertEquals("John",student.getFirstName());
    }
}