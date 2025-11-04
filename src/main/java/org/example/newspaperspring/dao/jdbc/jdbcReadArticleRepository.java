package org.example.newspaperspring.dao.jdbc;

import org.example.newspaperspring.dao.ReadArticleRepository;
import org.example.newspaperspring.dao.mappers.jdbc_mappers.ReadArticleMapperDao;
import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.example.newspaperspring.dao.utils.DBConnectionPool;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.error.DatabaseError;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Profile("jdbc")
@Repository
public class jdbcReadArticleRepository implements ReadArticleRepository {

    private final DBConnectionPool dbConnectionPool;
    private final ReadArticleMapperDao readArticleMapperDao;
    private final SQLQueries sqlQueries;


    public jdbcReadArticleRepository(DBConnectionPool dbConnectionPool, ReadArticleMapperDao readArticleMapperDao, SQLQueries sqlQueries) {
        this.dbConnectionPool = dbConnectionPool;
        this.readArticleMapperDao = readArticleMapperDao;
        this.sqlQueries = sqlQueries;
    }

    @Override
    public List<ReadArticleEntity> getAll() {
        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_READARTICLES_QUERY);
            return readArticleMapperDao.mapReadArticles(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }
    }

    @Override
    public ReadArticleEntity get(int id) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READARTICLE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            return readArticleMapperDao.mapReadArticle(rs);

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public int save(ReadArticleEntity readArticle) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READARTICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, readArticle.getArticleId());
            preparedStatement.setInt(2, readArticle.getReaderId());
            preparedStatement.setInt(3, readArticle.getRating());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int generatedId = rs.getInt(1);
                readArticle.setId(generatedId);
                return generatedId;
            } else {
                throw new DatabaseError("Failed to retrieve generated read-article ID.");
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());

        }
    }

    @Override
    public void update(ReadArticleEntity readArticle) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dbConnectionPool.getConnection();
            pstmt = conn.prepareStatement(SQLQueries.UPDATE_READARTICLE_QUERY);
            pstmt.setInt(1, readArticle.getRating());
            pstmt.setInt(2, readArticle.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } finally {
            dbConnectionPool.releaseResource(pstmt);
            dbConnectionPool.closeConnection(conn);
        }
    }

    @Override
    public void delete(ReadArticleEntity readArticle) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READARTICLE_QUERY)) {
            preparedStatement.setInt(1, readArticle.getReaderId());
            preparedStatement.setInt(2, readArticle.getArticleId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public void deleteByArticleId(int articleId) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READARTICLE_BY_ARTICLE_ID_QUERY)) {
            preparedStatement.setInt(1, articleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public List<ReadArticleEntity> getAllByArticleId(int articleId) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READARTICLE_BY_ARTICLE_ID_QUERY)) {
            preparedStatement.setInt(1,articleId);

            ResultSet rs = preparedStatement.executeQuery();
            return readArticleMapperDao.mapReadArticles(rs);

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }
}
