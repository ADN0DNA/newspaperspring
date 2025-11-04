package org.example.newspaperspring.dao.springjdbc;

import org.example.newspaperspring.dao.ReaderRepository;
import org.example.newspaperspring.dao.mappers.spring_mappers.ReaderRowMapper;
import org.example.newspaperspring.dao.model.ReaderEntity;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.error.ForeignKeyError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Profile("inUse")
@Repository
public class SpringReaderRepository implements ReaderRepository {
    @Autowired
    private JdbcClient jdbcClient;
    private final ReaderRowMapper readerRowMapper;

    public SpringReaderRepository(JdbcClient jdbcClient, ReaderRowMapper readerRowMapper) {
        this.jdbcClient = jdbcClient;
        this.readerRowMapper = readerRowMapper;
    }

    @Override
    public List<ReaderEntity> getAll() {
        return jdbcClient.sql(SQLQueries.SELECT_READERS_QUERY)
                .query(readerRowMapper)
                .list();
    }

    @Override
    public ReaderEntity get(int id) {
        return jdbcClient.sql(SQLQueries.SELECT_READER_BY_ID_QUERY)
                .param(1, id)
                .query(readerRowMapper)
                .optional()
                .orElseThrow(() -> new AppError("Reader not found: " + id));
    }

    @Override
    public int save(ReaderEntity reader) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(SQLQueries.INSERT_READER_QUERY)
                .param(1, reader.getName())
                .param(2, Date.valueOf(reader.getDob()))
                .update(keyHolder);
        return Objects.requireNonNull(keyHolder.getKey(), "Reader ID was not generated").intValue();
    }

    @Override
    public void update(ReaderEntity reader) {
        jdbcClient.sql(SQLQueries.UPDATE_READER_QUERY)
                .param(1, reader.getName())
                .param(2, Date.valueOf(reader.getDob()))
                .param(3, reader.getId())
                .update();
    }

    @Override
    public void saveCredentials(String username, String password, int readerId) {
        jdbcClient.sql(SQLQueries.INSERT_READER_CREDENTIALS_QUERY)
                .param(1, username)
                .param(2, password)
                .param(3, readerId)
                .update();
    }

    @Override
    public void deleteCredentialsByReaderId(int readerId) {
        jdbcClient.sql(SQLQueries.DELETE_READER_CREDENTIALS_BY_READER_ID_QUERY)
                .param(1, readerId)
                .update();
    }

    @Override
    public void delete(ReaderEntity reader) {
        try {
            jdbcClient.sql(SQLQueries.DELETE_READER_QUERY)
                    .param(1, reader.getId())
                    .update();
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyError("Reader has credentials.");
        }
    }
}
