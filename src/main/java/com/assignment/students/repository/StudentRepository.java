package com.assignment.students.repository;

import com.assignment.students.model.Class;
import com.assignment.students.model.EmailAddresses;
import com.assignment.students.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public long addStudent(Student student) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keys = {"student_id"};

        String sql = "INSERT INTO student (first_name,last_name) VALUES (:first_name,:last_name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name",student.getFirstName());
        params.addValue("last_name",student.getLastName());
        namedParameterJdbcTemplate.update(sql,params,keyHolder,keys);
        long student_id = keyHolder.getKey().longValue();

        if(student.getEmailAddresses().size() > 0) {
            for (EmailAddresses emailAddresses : student.getEmailAddresses()) {
                updateEmailAddresses(emailAddresses, student_id);
            }
        }

        if(student.getClasses().size() > 0){
            for (Class subject : student.getClasses()){
                updateClassesEnrolled(subject.getClassId(),student_id);
            }
        }

        return student_id;
    }

    private void updateClassesEnrolled(long classId, long studentId) {
        String sql = "INSERT INTO class_student (student_id, class_id) VALUES(:student_id,:class_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("student_id",studentId)
                .addValue("class_id",classId);
        namedParameterJdbcTemplate.update(sql,params);
    }

    private void updateEmailAddresses(EmailAddresses emailAddresses, long student_id) {
        String sql = "INSERT INTO email_addresses (student_id,email_address,email_type) VALUES (:student_id,:email_address,:email_type)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("student_id",student_id)
                .addValue("email_address",emailAddresses.getEmailAddress())
                .addValue("email_type", emailAddresses.getEmailType().toString());
        namedParameterJdbcTemplate.update(sql,params);
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";
        return namedParameterJdbcTemplate.query(sql,new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Student.class));
    }

    public void enrollStudent(long classId , long studentId) {
        updateClassesEnrolled(classId,studentId);
    }

    public void unEnrollStudent(long classId , long studentId) {
        unEnrollStudentFromClass(classId,studentId);
    }

    private void unEnrollStudentFromClass(long classId, long studentId) {
        String sql = "DELETE FROM class_student where student_id = :student_id AND class_id = :class_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("class_id", classId)
                .addValue("student_id",studentId);
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }


    public void deleteStudent(String id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("student_id",id);
        String sql = "DELETE FROM class_student where student_id = :student_id";
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
        sql = "DELETE FROM email_addresses where student_id = :student_id";
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
        sql = "DELETE FROM student where student_id = :student_id";
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }

    public Student getStudentById(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("student_id",id);
        String sql = "SELECT * FROM class where class_id IN (SELECT class_id from class_student where student_id = :student_id)";
        List<Class> classList = namedParameterJdbcTemplate.query(sql,mapSqlParameterSource,BeanPropertyRowMapper.newInstance(Class.class));
        sql = "SELECT * FROM email_addresses where student_id = :student_id";
        List<EmailAddresses> emailAddressesList = namedParameterJdbcTemplate.query(sql,mapSqlParameterSource,BeanPropertyRowMapper.newInstance(EmailAddresses.class));
        sql = "SELECT * FROM student where student_id = :student_id";
        Student student = namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,BeanPropertyRowMapper.newInstance(Student.class));
        student.setEmailAddresses(emailAddressesList);
        student.setClasses(classList);
        return student;
    }

}
