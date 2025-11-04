package org.example.newspaperspring.dao.springjdbc;

import org.example.newspaperspring.dao.TypeRepository;
import org.example.newspaperspring.dao.model.TypeEntity;
import org.example.newspaperspring.dao.mappers.spring_mappers.TypeRowMapper;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Profile("inUse")
@Repository
public class SpringTypeRepository implements TypeRepository {

    @Autowired
    private JdbcClient jdbcClient;
    private final TypeRowMapper typeRowMapper;

    public SpringTypeRepository(JdbcClient jdbcClient, TypeRowMapper typeRowMapper) {
        this.jdbcClient = jdbcClient;
        this.typeRowMapper = typeRowMapper;
    }

    @Override
    public List<TypeEntity> getAll() {
        return jdbcClient.sql(SQLQueries.SELECT_TYPES_QUERY)
                .query(typeRowMapper)
                .list();
    }

    @Override
    public TypeEntity get(int id) {
        return null;
    }

    @Override
    public int save(TypeEntity type) {
        return 0;
    }

    @Override
    public void update(TypeEntity type) {
    }

    @Override
    public void delete(TypeEntity type) {
    }
}

