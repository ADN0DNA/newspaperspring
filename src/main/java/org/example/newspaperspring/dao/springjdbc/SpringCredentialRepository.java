package org.example.newspaperspring.dao.springjdbc;

import org.example.newspaperspring.dao.CredentialRepository;
import org.example.newspaperspring.dao.model.CredentialEntity;
import org.example.newspaperspring.dao.mappers.spring_mappers.CredentialRowMapper;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Profile("inUse")
@Repository
public class SpringCredentialRepository implements CredentialRepository {
    @Autowired
    private JdbcClient jdbcClient;
    private final RowMapper<CredentialEntity> credentialRowMapper;

    public SpringCredentialRepository(JdbcClient jdbcClient, CredentialRowMapper credentialRowMapper) {
        this.jdbcClient = jdbcClient;
        this.credentialRowMapper = credentialRowMapper;
    }

    @Override
    public List<CredentialEntity> getAll() {
        return jdbcClient.sql("select * from credential").query(credentialRowMapper).list();
    }

    @Override
    public CredentialEntity get(String username) {
        return jdbcClient.sql(SQLQueries.SELECT_CREDENTIAL_BY_USERNAME_QUERY)
                .param(1, username)
                .query(credentialRowMapper)
                .optional()
                .orElseThrow(() -> new AppError("Credential not found: " + username));
    }

    @Override
    public int save(CredentialEntity credential) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(SQLQueries.INSERT_READER_CREDENTIALS_QUERY)
                .param(1, credential.getUsername())
                .param(2, credential.getPassword())
                .param(3, credential.getIdReader())
                .update(keyHolder);
        return Objects.requireNonNull(keyHolder.getKey(), "Credential ID was not generated").intValue();
    }

    @Override
    public void update(CredentialEntity credential) {
        jdbcClient.sql(SQLQueries.UPDATE_READER_CREDENTIALS_QUERY)
                .param(1, credential.getPassword())
                .param(2, credential.getUsername())
                .update();
    }

    @Override
    public void delete(CredentialEntity credential) {
        jdbcClient.sql(SQLQueries.DELETE_READER_CREDENTIALS_BY_READER_ID_QUERY)
                .param(1, credential.getIdReader())
                .update();
    }
}
