package org.example.newspaperspring.dao.mappers.spring_mappers;

import org.example.newspaperspring.dao.model.CredentialEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CredentialRowMapper implements RowMapper<CredentialEntity> {
    @Override
    public CredentialEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setUsername(rs.getString("username"));
        credentialEntity.setPassword(rs.getString("password"));
        credentialEntity.setIdReader(rs.getInt("reader_id"));
        return credentialEntity;
    }
}

