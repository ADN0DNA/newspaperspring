package org.example.newspaperspring.dao.springjdbc;

import org.example.newspaperspring.dao.NewspaperRepository;
import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.example.newspaperspring.dao.mappers.spring_mappers.NewspaperRowMapper;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Profile("inUse")
@Repository
public class SpringNewspaperRepository implements NewspaperRepository {

    @Autowired
    private JdbcClient jdbcClient;
    private final NewspaperRowMapper newspaperRowMapper;

    public SpringNewspaperRepository(JdbcClient jdbcClient, NewspaperRowMapper newspaperRowMapper) {
        this.jdbcClient = jdbcClient;
        this.newspaperRowMapper = newspaperRowMapper;
    }

    @Override
    public List<NewspaperEntity> getAll() {
        return jdbcClient.sql(SQLQueries.SELECT_NEWSPAPERS_QUERY)
                .query(newspaperRowMapper)
                .list();
    }

    @Override
    public NewspaperEntity get(int id) {
        return jdbcClient.sql(SQLQueries.SELECT_NEWSPAPER_BY_ID_QUERY)
                .param(1, id)
                .query(newspaperRowMapper)
                .optional()
                .orElseThrow(() -> new AppError("Newspaper not found: " + id));
    }

    @Override
    public int save(NewspaperEntity newspaper) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(SQLQueries.INSERT_NEWSPAPER_QUERY)
                .param(1, newspaper.getId())
                .param(2, newspaper.getName())
                .param(3, Date.valueOf(newspaper.getReleaseDate()))
                .update(keyHolder);
        return Objects.requireNonNull(keyHolder.getKey(), "Newspaper ID was not generated").intValue();
    }

    @Override
    public void update(NewspaperEntity newspaper) {
        jdbcClient.sql(SQLQueries.UPDATE_NEWSPAPER_QUERY)
                .param(1, newspaper.getName())
                .param(2, newspaper.getId())
                .update();
    }

    @Override
    public void delete(NewspaperEntity newspaper) {
        jdbcClient.sql(SQLQueries.DELETE_NEWSPAPER_QUERY)
                .param(1, newspaper.getId())
                .update();
    }

    @Override
    public List<NewspaperEntity> getAllByReader(int readerId) {
        return jdbcClient.sql(SQLQueries.SELECT_NEWSPAPERS_BY_READER_QUERY)
                .param(1, readerId)
                .query(newspaperRowMapper)
                .list();
    }
}

