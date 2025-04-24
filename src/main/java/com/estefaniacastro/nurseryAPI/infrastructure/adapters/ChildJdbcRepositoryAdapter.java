package com.estefaniacastro.nurseryAPI.infrastructure.adapters;

import com.estefaniacastro.nurseryAPI.domain.model.Child;
import com.estefaniacastro.nurseryAPI.domain.port.out.ChildRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class ChildJdbcRepositoryAdapter implements ChildRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChildJdbcRepositoryAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Child> rowMapper = (rs, rowNum) -> new Child(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getDate("birth_date").toLocalDate()
    );

    @Override
    public List<Child> findAll() {
        return jdbcTemplate.query("SELECT * FROM children", rowMapper);
    }

    @Override
    public Optional<Child> findById(Long id) {
        String sql = "SELECT * FROM children WHERE id = ?";
        List<Child> children = jdbcTemplate.query(sql, rowMapper, id);
        return children.stream().findFirst();
    }

    @Override
    public Child save(Child child) {
        String sql = """
            INSERT INTO children (first_name, last_name, birth_date)
            VALUES (?, ?, ?)
        """;

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, child.firstName());
            ps.setString(2, child.lastName());
            ps.setObject(3, child.birthDate());
            return ps;
        }, keyHolder);

        // In a real case you'd retrieve the generated ID
        Long generatedId = keyHolder.getKey().longValue();

        return new Child(
                generatedId,
                child.firstName(),
                child.lastName(),
                child.birthDate()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM children WHERE id = ?", id);
    }
}
