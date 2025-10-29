package org.example.newspaperspring.dao.mappers.jdbc_mappers;

import org.example.newspaperspring.dao.model.CredentialEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class CredentialMapperDao {
    public CredentialEntity mapCredential(java.sql.ResultSet rs) throws SQLException {
        if (rs.next()) {
            CredentialEntity credential = new CredentialEntity();
            credential.setUsername(rs.getString("username"));
            credential.setPassword(rs.getString("password"));
            credential.setIdReader(rs.getInt("reader_id"));
            return credential;
        }
        return null;
    }
}
