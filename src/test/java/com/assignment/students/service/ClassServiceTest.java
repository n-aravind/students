package com.assignment.students.service;

import com.assignment.students.model.Class;
import com.assignment.students.repository.ClassRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassServiceTest {

    private ClassRepository classRepository = mock(ClassRepository.class);
    private ClassService classService = new ClassService(classRepository);

    @Test
    void addClass() {
        Class subject = Class.builder().classId(1).className("Biology").classDescription("Test Description").build();
        classService.addClass(subject);
        ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);
        verify(classRepository,times(1)).addClass(subject);
        verify(classRepository).addClass(classArgumentCaptor.capture());
        assertEquals("Biology",classArgumentCaptor.getValue().getClassName());
    }

    @Test
    void getAllClasses() {
        Class subject1 = Class.builder().className("Biology").classDescription("Test Description").build();
        Class subject2 = Class.builder().className("Algebra").classDescription("Test Description").build();
        List<Class> classList = new ArrayList<>();
        classList.add(subject1);
        classList.add(subject2);
        when(classService.getAllClasses()).thenReturn(classList);
        assertEquals(2,classService.getAllClasses().size());
        verify(classRepository,times(1)).getAllClasses();
    }

    @Test
    void getClassById() {
        when(classService.getClassById(1)).thenReturn(Class.builder().className("History").classDescription("Test Description").build());
        Class subject = classService.getClassById(1);
        assertEquals("History",subject.getClassName());
        assertEquals("Test Description",subject.getClassDescription());
    }

    @Test
    void updateClass() {
        Class subject = Class.builder().className("Algebra").classDescription("Test Description").build();
        classService.addClass(subject);
        subject.setClassDescription("Changed Description");
        classService.updateClass(subject);
        ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);
        verify(classRepository,times(1)).updateClass(subject);
        verify(classRepository).updateClass(classArgumentCaptor.capture());
        assertEquals("Changed Description",classArgumentCaptor.getValue().getClassDescription());
    }

    @Test
    void deleteClass() {
        classService.deleteClass("Algebra");
        verify(classRepository,times(1)).deleteClass("Algebra");
    }

    @Test
    void addClasses() {
        Class subject1 = Class.builder().className("Biology").classDescription("Test Description").build();
        Class subject2 = Class.builder().className("Algebra").classDescription("Test Description").build();
        List<Class> classList = new ArrayList<>();
        classList.add(subject1);
        classList.add(subject2);
        classService.addClasses(classList);
        verify(classRepository,times(1)).addClass(subject1);
        verify(classRepository,times(1)).addClass(subject2);
    }
}