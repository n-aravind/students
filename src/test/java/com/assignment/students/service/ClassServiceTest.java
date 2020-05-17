package com.assignment.students.service;

import com.assignment.students.model.Class;
import com.assignment.students.repository.ClassRepository;
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
class ClassServiceTest {

    private ClassRepository classRepository = mock(ClassRepository.class);
    private ClassService classService = new ClassService(classRepository);

    @Test
    void addClass() {
        Class subject = Class.builder().classId(1).className("Biology").classDescription("Test Description").build();
        classService.addClass(subject);
        Mockito.when(classRepository.addClass(notNull())).thenReturn(1L);
        ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);
        verify(classRepository,times(1)).addClass(subject);
        verify(classRepository).addClass(classArgumentCaptor.capture());
        assertEquals("Biology",classArgumentCaptor.getValue().getClassName());
        assertEquals(1,classRepository.addClass(subject));
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
        Class subject = Class.builder().classId(1).className("Algebra").classDescription("Test Description").build();
        classService.addClass(subject);
        subject.setClassDescription("Changed Description");
        classService.updateClass(1,subject);
        verify(classRepository,times(1)).updateClass(1,subject);
    }

    @Test
    void deleteClass() {
        classService.deleteClass(1);
        verify(classRepository,times(1)).deleteClass(1);
    }

    @Test
    public void dataIntegrityViolationException(){
        doThrow(DataIntegrityViolationException.class)
                .when(classRepository)
                .addClass(Class.builder().build());
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> classService.addClass(Class.builder().build()));
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