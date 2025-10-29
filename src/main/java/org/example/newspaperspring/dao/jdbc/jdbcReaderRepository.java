package org.example.newspaperspring.dao.jdbc;

import org.example.newspaperspring.dao.ReaderRepository;
import org.example.newspaperspring.dao.mappers.jdbc_mappers.ReaderMapperDao;
import org.example.newspaperspring.dao.model.ReaderEntity;
import org.example.newspaperspring.dao.utils.DBConnectionPool;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.error.DatabaseError;
import org.example.newspaperspring.domain.error.ForeignKeyError;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Profile("jdbc")
@Repository
public class jdbcReaderRepository implements ReaderRepository {
    private final ReaderMapperDao readerMapperDao;
    private final SQLQueries sqlQueries;
    private final DBConnectionPool dbConnectionPool;


    public jdbcReaderRepository(ReaderMapperDao readerMapperDao,SQLQueries sqlQueries, DBConnectionPool dbConnectionPool) {
        this.readerMapperDao = readerMapperDao;
        this.sqlQueries = sqlQueries;
        this.dbConnectionPool = dbConnectionPool;
    }

    @Override
    public List<ReaderEntity> getAll() {
        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_READERS_QUERY);
            return readerMapperDao.mapReaders(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }
    }

    @Override
    public ReaderEntity get(int id) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            return readerMapperDao.mapReader(rs);

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public int save(ReaderEntity reader) {
        try (Connection conn = dbConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.INSERT_READER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reader.getName());
            pstmt.setDate(2, Date.valueOf(reader.getDob()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseError("Creating reader failed, no rows affected.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DatabaseError("Creating reader failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public void update(ReaderEntity reader) {

    }


    @Override
    public void saveCredentials(String username, String password, int readerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnectionPool.getConnection();
            pstmt = conn.prepareStatement(SQLQueries.INSERT_READER_CREDENTIALS_QUERY);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, readerId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new DatabaseError(e.getMessage());
            }
        }
    }

    public void deleteCredentialsByReaderId(int readerId) {
        try (Connection conn = dbConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.DELETE_READER_CREDENTIALS_BY_READER_ID_QUERY)) {
            pstmt.setInt(1, readerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public void delete(ReaderEntity reader) {
        try (Connection conn = dbConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.DELETE_READER_QUERY)) {
            pstmt.setInt(1, reader.getId());
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ForeignKeyError("Reader has credentials.");
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

}
