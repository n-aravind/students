package com.assignment.students.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestControllerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DuplicateKeyException.class)
    protected ResponseEntity<ErrorResponse> duplicateCourse(DuplicateKeyException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder().timestamp(LocalDateTime.now())
                .message("Course With this name already exists")
                .details(ex.getClass().getSimpleName()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> courseCannotBeDeleted(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder().timestamp(LocalDateTime.now())
                .message("Cannot delete course when students are enrolled in it")
                .details(ex.getClass().getSimpleName()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> courseCannotBeDeleted(EmptyResultDataAccessException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder().timestamp(LocalDateTime.now())
                .message("Record Not Present In the Database")
                .details(ex.getClass().getSimpleName()).build(), HttpStatus.BAD_REQUEST);
    }

}
