package com.assignment.students.controller;

import com.assignment.students.model.Course;
import com.assignment.students.model.Student;
import com.assignment.students.service.CourseService;
import com.assignment.students.service.CourseStudentService;
import com.assignment.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final CourseStudentService courseStudentService;

    public WebController(CourseService courseService, StudentService studentService, CourseStudentService courseStudentService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.courseStudentService = courseStudentService;
    }

    @GetMapping("/v1/students")
    public String studentForm(Model model){
        model.addAttribute("student",new Student());
        return"student-form";
    }

    @GetMapping("/v1/courses")
    public String courseForm(Model model){
        model.addAttribute("course",new Course());
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-form";
    }


    @GetMapping("/v1/courses/{id}/delete")
    public String courseForm(@PathVariable long id,  Model model){
        courseService.deleteCourse(id);
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-registry";
    }

    @GetMapping("/v1/courses/{courseId}/unenroll/{studentId}")
    public String unEnrollStudentFromCourse(@PathVariable long courseId, @PathVariable long studentId, Model model){
        courseStudentService.unEnrollStudentFromCourse(courseId,studentId);
        getCourseDetails(courseId, model);
        return "course-details";
    }

    @GetMapping("/v1/courses/{id}/studentsEnrolled")
    public String getStudentsByCourseId(@PathVariable long id, Model model){
        getCourseDetails(id, model);
        return "course-details";
    }

    private void getCourseDetails(@PathVariable long id, Model model) {
        Course subject = courseService.getCourseById(id);
        List<Student> studentsList = new ArrayList<>();
        for (Long studentId : courseStudentService.getStudentIdsByCourseId(id)){
            Student student = studentService.getStudentById(studentId);
            studentsList.add(student);
        }
        model.addAttribute("course",subject);
        if(studentsList.size() == 0){
            model.addAttribute("students","no-data");
        }else {
            model.addAttribute("students", studentsList);
        }
    }


    @PostMapping(path = "/v1/courses",params="action=save")
    public String courseSubmit(@Valid Course subject, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "course-form";
        }

        courseService.addCourse(subject);
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-registry";
    }

    @PostMapping(path = "/v1/courses",params="action=cancel")
    public String cancelFormSubmission(@Valid Course subject, BindingResult result, Model model){
        return "course-registry";
    }

    @PostMapping(path = "/v1/courses/{id}")
    public String updateCourse(@PathVariable long id, @Valid Course subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "course";
        }
        courseService.updateCourse(id, subject);
        model.addAttribute("coursees", courseService.getAllCourses());
        return "course-registry";
    }

    @GetMapping("/v1/courses/all")
    public String getAllCourses(Model model){
        model.addAttribute("courses", courseService.getAllCourses());
        return "course-registry";
    }

    @GetMapping("/v1/students/all")
    public String getAllStudents(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }

    @GetMapping("/v1/students/{studentId}")
    public String getStudent(@PathVariable long studentId,  Model model){
        getStudentDetails(studentId, model);
        return "student-details";
    }

    @GetMapping("/v1/students/{studentId}/unenroll/{courseId}")
    public String unEnrollCourseFromStudent(@PathVariable long studentId, @PathVariable long courseId, Model model){
        courseStudentService.unEnrollStudentFromCourse(courseId,studentId);
        getStudentDetails(studentId, model);
        return "student-details";
    }

    private void getStudentDetails(@PathVariable long studentId, Model model) {
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        if (student.getCourses().size() == 0) {
            model.addAttribute("enrolledCourses", "none");
        }
    }

    @GetMapping("/v1/students/{studentId}/enroll/{courseId}")
    public String enrollStudentIntoCourse(@PathVariable long studentId, @PathVariable long courseId, Model model){
        courseStudentService.enrollStudentIntoCourse(courseId,studentId);
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student",student);
        return "student-details";
    }

    @GetMapping("/v1/students/{studentId}/enroll")
    public String getAvailableCoursesForStudent(@PathVariable long studentId, Model model){
        model.addAttribute("courses",studentService.getUnEnrolledCourses(studentId));
        model.addAttribute("student_id",studentId);
        return "available-courses";
    }

    @GetMapping("/v1/students/{studentId}/delete")
    public String getStudent(@PathVariable String studentId,  Model model){
        studentService.deleteStudent(studentId);
        model.addAttribute("students",studentService.getAllStudents());
        return "student-registry";
    }

    @PostMapping("/v1/students")
    public String studentSubmit(@Valid Student student, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "student";
        }
        student.setCourses(new ArrayList<>());
        studentService.addStudent(student);
        model.addAttribute("student",student);
        model.addAttribute("enrolledCourses", "none");
        return "student-details";
    }
}
