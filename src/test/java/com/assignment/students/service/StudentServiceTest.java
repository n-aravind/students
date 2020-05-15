package com.assignment.students.service;

import com.assignment.students.model.Class;
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
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).addStudent(studentArgumentCaptor.capture());
        assertEquals("Algebra", studentArgumentCaptor.getValue().getClasses().get(0).getClassName());
        assertEquals(EmailType.PERSONAL, studentArgumentCaptor.getValue().getEmailAddresses().get(0).getEmailType());
    }

    @Test
    void getAllStudents() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        when(studentService.getAllStudents()).thenReturn(studentList);
        assertEquals(1, studentService.getAllStudents().size());
        verify(studentRepository, times(1)).getAllStudents();
    }

    @Test
    void enrollStudent() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().classId(1).className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        classList.add(Class.builder().classId(2).className("History").classDescription("Test Description").build());
        student.setClasses(classList);
        studentService.enrollStudent(student);
        verify(studentRepository,times(1)).enrollStudent(1L,0L);
        verify(studentRepository,times(1)).enrollStudent(2L,0L);
    }

    @Test
    void unEnrollStudent() {
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().classId(1).className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        Student student = Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build();
        studentService.addStudent(student);
        student.setClasses(classList);
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
        List<Class> classList = new ArrayList<>();
        classList.add(Class.builder().className("Algebra").classDescription("Test Description").build());
        List<EmailAddresses> emailAddressesList = new ArrayList<>();
        emailAddressesList.add(EmailAddresses.builder().emailAddress("john.doe@gmail.com").emailType(EmailType.PERSONAL).build());
        when(studentService.getStudentById(1)).thenReturn(Student.builder().firstName("John").lastName("Doe").emailAddresses(emailAddressesList).classes(classList).build());
        Student student = studentService.getStudentById(1);
        assertEquals("John", student.getFirstName());
    }
}