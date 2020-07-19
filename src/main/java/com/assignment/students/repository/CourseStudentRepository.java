package com.assignment.students.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseStudentRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CourseStudentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> getStudentIdsByCourseId(long courseId) {
        String sql = "SELECT student_id FROM course_student where course_id = :course_id";
        SqlParameterSource param = new MapSqlParameterSource("course_id", courseId);
        return namedParameterJdbcTemplate.queryForList(sql,param,Long.class);
    }

    public List<Long> getCourseIdsByStudentId(long studentId) {
        String sql = "SELECT course_id FROM course_student where student_id = :student_id";
        SqlParameterSource param = new MapSqlParameterSource("student_id", studentId);
        return namedParameterJdbcTemplate.queryForList(sql,param,Long.class);
    }

    public void unEnrollStudentFromCourse(long courseId,long studentId){
        String sql = "DELETE FROM course_student where course_id = :course_id and student_id = :student_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("course_id",courseId);
        params.addValue("student_id",studentId);
        namedParameterJdbcTemplate.update(sql,params);
    }
}
