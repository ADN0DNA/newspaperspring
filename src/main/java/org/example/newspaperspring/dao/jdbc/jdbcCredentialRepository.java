package org.example.newspaperspring.dao.jdbc;

import org.example.newspaperspring.dao.CredentialRepository;
import org.example.newspaperspring.dao.mappers.jdbc_mappers.CredentialMapperDao;
import org.example.newspaperspring.dao.model.CredentialEntity;
import org.example.newspaperspring.dao.utils.DBConnectionPool;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.DatabaseError;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("jdbc")
@Repository
public class jdbcCredentialRepository implements CredentialRepository {

    private final SQLQueries sqlQueries;
    private final DBConnectionPool dbConnectionPool;
    private final CredentialMapperDao credentialMapperDao;

    public jdbcCredentialRepository( SQLQueries sqlQueries,
                                  DBConnectionPool dbConnectionPool, CredentialMapperDao credentialMapperDao) {
        this.sqlQueries = sqlQueries;
        this.dbConnectionPool = dbConnectionPool;
        this.credentialMapperDao = credentialMapperDao;
    }



    @Override
    public List<CredentialEntity> getAll() {
        return List.of();
    }

    @Override
    public CredentialEntity get(String username) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CREDENTIAL_BY_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            return credentialMapperDao.mapCredential(rs);

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public int save(CredentialEntity credential) {
        return 0;
    }

    @Override
    public void update(CredentialEntity credential) {

    }

    @Override
    public void delete(CredentialEntity credential) {

    }
}
