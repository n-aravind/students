package com.assignment.students.repository;

import com.assignment.students.model.Class;
import com.assignment.students.model.EmailAddresses;
import com.assignment.students.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

// GP @Transactional should be in the service not the repository
@Repository
public class StudentRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public void addStudent(Student student) {

        String sql = "INSERT INTO student (first_name,last_name) VALUES (:first_name,:last_name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name",student.getFirstName());
        params.addValue("last_name",student.getLastName());
        namedParameterJdbcTemplate.update(sql,params);

        // GP if no email addresses and no classes do not need to query for student id.

        // GP do not query for id. We are going to get back the PK in a different way.
        // https://github.com/gpratte/texastoc-v2-spring-boot/tree/master/application/src/main/java/com/texastoc/repository
        sql = "SELECT * from student where first_name = :first_name and last_name = :last_name";

        long student_id = Objects.requireNonNull(namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Student.class))).getStudentId();

        if(student.getEmailAddresses().size() > 0) {
            for (EmailAddresses emailAddresses : student.getEmailAddresses()) {
                updateEmailAddresses(emailAddresses, student_id);
            }
        }

        if(student.getClasses().size() > 0){
            for (Class subject : student.getClasses()){
                updateClassesEnrolled(subject,student_id);
            }
        }

    }

    private void updateClassesEnrolled(Class subject, long student_id) {
        // GP class should have the ID
        String sql = "SELECT * FROM class where class_name = :class_name";
        long class_id = Objects.requireNonNull(namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("class_name", subject.getClassName()), BeanPropertyRowMapper.newInstance(Class.class))).getClassId();

        sql = "INSERT INTO class_student (student_id, class_id) VALUES(:student_id,:class_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("student_id",student_id)
                .addValue("class_id",class_id);
        namedParameterJdbcTemplate.update(sql,params);
    }

    private void updateEmailAddresses(EmailAddresses emailAddresses, long student_id) {
        String sql = "INSERT INTO email_addresses (student_id,email_address,email_type) VALUES (:student_id,:email_address,:email_type)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("student_id",student_id)
                .addValue("email_address",emailAddresses.getEmailAddress())
                .addValue("email_type",emailAddresses.getEmailType());
        namedParameterJdbcTemplate.update(sql,params);
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";
        return namedParameterJdbcTemplate.query(sql,new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Student.class));
    }

    // GP maybe make it easier
    //public void enrollStudent(long studentId, long classId) {
    public void enrollStudent(Student student) {
        for (Class subject : student.getClasses()){
            updateClassesEnrolled(subject,student.getStudentId());
        }
    }

    // GP ditto
    public void unEnrollStudent(Student student) {
        for(Class subject : student.getClasses()){
            unEnrollStudentFromClass(subject,student.getStudentId());
        }
    }

    // GP just work with ids (no query)
    private void unEnrollStudentFromClass(Class subject, long studentId) {
        String sql = "SELECT * FROM class where class_name = :class_name";
        long class_id = Objects.requireNonNull(namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("class_name", subject.getClassName()), BeanPropertyRowMapper.newInstance(Class.class))).getClassId();

        sql = "DELETE FROM class_student where student_id = :student_id AND class_id = :class_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("class_id", class_id)
                .addValue("student_id",studentId);
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);

    }

    @Transactional
    public void deleteStudent(String id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("student_id",id);
        String sql = "DELETE FROM class_student where student_id = :student_id";
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
        // GP read database cascade delete (but we are not doing a cascade delete so this is fine)
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
