package com.turanbalayev.dao;

import com.turanbalayev.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseJdbcDAO implements DAO<Course> {
    public static final Logger log = LoggerFactory.getLogger(CourseJdbcDAO.class);
    private JdbcTemplate jdbcTemplate;

    public CourseJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Course> courseRowMapper = (rs, rowNum) -> {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));
        course.setLink(rs.getString("link"));
        return course;
    };

    @Override
    public List<Course> list() {
        String sql = "SELECT id, title, description, link FROM ultimate_jdbc.course";
        return jdbcTemplate.query(sql, courseRowMapper);
    }

    @Override
    public void create(Course course) {
        String sql = "INSERT INTO course(title,description,link) VALUES(?,?,?)";
        int insert = jdbcTemplate.update(sql,course.getTitle(),course.getDescription(),course.getLink());
        if(insert == 1) {
            log.info("New course created: {}",course.getTitle());
        }
    }

    @Override
    public Optional<Course> get(int id) {
        String sql = "SELECT id,title,description,link FROM course WHERE id = ?";
        Course course = null;

        try {
            course = jdbcTemplate.queryForObject(sql, new Object[]{id},courseRowMapper);
        } catch (DataAccessException exception) {
            log.error("Course not found: {}",id);
        }

        return Optional.ofNullable(course);
    }

    @Override
    public void update(Course course, int id) {
        String sql = "UPDATE course SET title = ?, description = ?, link =? WHERE id = ?";
        int update = jdbcTemplate.update(sql,course.getTitle(),course.getDescription(),course.getLink(),course.getId());
        if(update == 1) {
            log.info("The course updated: {}",course.getTitle());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM course WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }
}
