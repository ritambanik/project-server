package com.example.project;

import com.example.project.data.Project;
import com.example.project.data.ProjectForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

import static com.example.project.data.Project.createProjectBuilder;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class ProjectRepository {

    private JdbcTemplate jdbcTemplate;

    private KeyHolder keyHolder = new GeneratedKeyHolder();

    public ProjectRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Project create(ProjectForm projectForm) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO project(name, active) VALUES (?, ?)", RETURN_GENERATED_KEYS);
            ps.setString(1, projectForm.getName());
            ps.setBoolean(2, projectForm.isActive());
            return ps;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        return jdbcTemplate.queryForObject("SELECT id, name , active FROM project WHERE id = " + id, rowMapper);
    }

    public Optional<Project> findById(int id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT id, name, active FROM project WHERE id = " + id, rowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void update (int id, boolean status) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("UPDATE project SET active = ? WHERE id = ?");
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            return ps;
        });
    }

    private RowMapper<Project> rowMapper = ((rs, rowNum) ->
        createProjectBuilder().id(rs.getInt("id"))
                .name(rs.getString("name")).active(rs.getBoolean("active")).build()
    );

}
