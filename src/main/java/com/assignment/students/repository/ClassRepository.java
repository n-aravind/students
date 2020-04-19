package com.assignment.students.repository;

import com.assignment.students.model.Class;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addClass(Class subject) {
        String query = "INSERT INTO CLASS (class_name,class_description) values (:class_name,:class_description)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("class_name",subject.getClassName());
        params.addValue("class_description",subject.getClassDescription());
        namedParameterJdbcTemplate.update(query,params);
    }

    public List<Class> getAllClasses() {
        String sql = "SELECT * FROM Class";
        SqlParameterSource param = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Class.class));
    }

    public Class getClassById(long id) {
        String sql = "SELECT * FROM Class where class_id = :class_id";
        SqlParameterSource param = new MapSqlParameterSource("class_id",id);
        return namedParameterJdbcTemplate.queryForObject(sql,param,BeanPropertyRowMapper.newInstance(Class.class));
    }

    public void updateClass(Class subject) {
        String sql = "Update Class Set class_description = :class_description where class_name = :class_name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("class_name",subject.getClassName())
                .addValue("class_description",subject.getClassDescription());
        namedParameterJdbcTemplate.update(sql,params);
    }

    public void deleteClass(String name) {
        String sql = "DELETE FROM Class where class_name = :class_name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("class_name",name);
        namedParameterJdbcTemplate.update(sql,params);
    }
}
