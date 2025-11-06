package org.example.newspaperspring.dao.jdbc;


import org.example.newspaperspring.dao.ArticleRepository;
import org.example.newspaperspring.dao.mappers.jdbc_mappers.ArticleMapperDao;
import org.example.newspaperspring.dao.model.ArticleEntity;
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
public class jdbcArticleRepository implements ArticleRepository {
    private final ArticleMapperDao articleMapperDao;
    private final SQLQueries sqlQueries;
    private final DBConnectionPool dbConnectionPool;


    public jdbcArticleRepository(ArticleMapperDao articleMapperDao, SQLQueries sqlQueries,
                                 DBConnectionPool dbConnectionPool) {
        this.articleMapperDao = articleMapperDao;
        this.sqlQueries = sqlQueries;
        this.dbConnectionPool = dbConnectionPool;
    }

    @Override
    public List<ArticleEntity> getAll() {

        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_ARTICLES_QUERY);
            return articleMapperDao.mapArticles(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }

    }


    @Override
    public ArticleEntity get(int id) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            return articleMapperDao.mapArticle(rs);

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }

    }

    @Override
    public int save(ArticleEntity article) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQLQueries.INSERT_ARTICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, article.getName());
            pstmt.setInt(2, article.getType().getId());
            pstmt.setInt(3, article.getNPaperId());
            //pstmt.setInt(4, 0);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    article.setId(generatedId);
                    return generatedId;
                } else {
                    throw new DatabaseError("Failed to retrieve generated article ID.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());
        }

    }

    @Override
    public void update(ArticleEntity article) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_ARTICLE_QUERY)) {
            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getType().getId());
            preparedStatement.setInt(3, article.getNPaperId());
            preparedStatement.setInt(4, article.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public void delete(int articleId, boolean confirmation) {

        try (Connection con =dbConnectionPool.getConnection();
        PreparedStatement deleteArticle = con.prepareStatement(SQLQueries.DELETE_ARTICLE_QUERY);
        PreparedStatement deleteReadArticle = con.prepareStatement(SQLQueries.DELETE_READARTICLE_QUERY))
        {
        try {
            con.setAutoCommit(false);
            if (confirmation) {
                deleteReadArticle.setInt(1, articleId);
                deleteReadArticle.executeUpdate();}

                deleteArticle.setInt(1, articleId);
                deleteArticle.executeUpdate();
            con.commit();
        }
        catch (SQLIntegrityConstraintViolationException e){
            con.rollback();
            throw new ForeignKeyError("THIS ARTICLE HAS A RATING, DO YOU WANT TO DELETE IT WITH ALL ITS CORRESPONDENCES?");
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getAverageRating(int articleId) {
        System.out.println("WAS USING SPRING SO THIS METHOD IS NOT IMPLEMENTED");
        return 0;
    }


}
