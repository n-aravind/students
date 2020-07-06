package com.assignment.students.repository;

import com.assignment.students.model.Course;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CourseRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public long addCourse(Course subject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO COURSE (course_name,course_description) values (:course_name,:course_description)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("course_name", subject.getCourseName());
        params.addValue("course_description", subject.getCourseDescription());
        String[] keys = {"course_id"};
        namedParameterJdbcTemplate.update(query, params, keyHolder, keys);
        return keyHolder.getKey().longValue();
    }

    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM COURSE";
        SqlParameterSource param = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Course.class));
    }

    public Course getCourseById(long id) {
        String sql = "SELECT * FROM COURSE where course_id = :course_id";
        SqlParameterSource param = new MapSqlParameterSource("course_id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Course.class));
    }

    public void updateCourse(long id, Course subject) {
        String sql = "Update Course Set course_name = :course_name, course_description = :course_description where course_id = :course_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("course_name", subject.getCourseName())
                .addValue("course_description", subject.getCourseDescription())
                .addValue("course_id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteCourse(long id) {
        String sql = "DELETE FROM Course where course_id = :course_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("course_id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
