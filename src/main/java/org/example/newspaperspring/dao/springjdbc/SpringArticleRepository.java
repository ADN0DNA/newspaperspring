package org.example.newspaperspring.dao.springjdbc;

import org.example.newspaperspring.dao.ArticleRepository;
import org.example.newspaperspring.dao.ReadArticleRepository;
import org.example.newspaperspring.dao.model.ArticleEntity;
import org.example.newspaperspring.dao.mappers.spring_mappers.ArticleRowMapper;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.error.ForeignKeyError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Profile("inUse")
@Repository
public class SpringArticleRepository implements ArticleRepository {

    @Autowired
    private JdbcClient jdbcClient;
    private final ArticleRowMapper articleRowMapper;
    private final ReadArticleRepository readArticleRepository;

    public SpringArticleRepository(JdbcClient jdbcClient, ArticleRowMapper articleRowMapper, ReadArticleRepository readArticleRepository) {
        this.jdbcClient = jdbcClient;
        this.articleRowMapper = articleRowMapper;
        this.readArticleRepository = readArticleRepository;
    }

    @Override
    public List<ArticleEntity> getAll() {
        return jdbcClient.sql(SQLQueries.SELECT_ARTICLES_QUERY)
                .query(articleRowMapper)
                .list();
    }

    @Override
    public ArticleEntity get(int id) {
        return jdbcClient.sql(SQLQueries.SELECT_ARTICLE_BY_ID_QUERY)
                .param(1, id)
                .query(articleRowMapper).optional()
                .orElseThrow(() -> new AppError("Article not found: " + id));
    }

    @Override
    public int save(ArticleEntity article) {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcClient.sql(SQLQueries.INSERT_ARTICLE_QUERY)
                    .param(1, article.getName())
                    .param(2, article.getType().getId())
                    .param(3, article.getNPaperId())
                    .update(keyHolder);
            return Objects.requireNonNull(keyHolder.getKey(), "Key was not generated").intValue();

    }

    @Override
    public void update(ArticleEntity article) {
        jdbcClient.sql(SQLQueries.UPDATE_ARTICLE_QUERY)
                .param(1, article.getName())
                .param(2, article.getType().getId())
                .param(3, article.getNPaperId())
                .param(4, article.getId())
                .update();
    }

    @Override
    public void delete(int articleId, boolean confirmation) {
        try {
            if (confirmation) {
                readArticleRepository.deleteByArticleId(articleId);
            }
            jdbcClient.sql(SQLQueries.DELETE_ARTICLE_QUERY)
                    .param(1, articleId)
                    .update();
        }catch (DataIntegrityViolationException e) {
            throw new ForeignKeyError("Cannot delete article with id " + articleId + " due to existing references in read articles.");
        }

    }

    @Override
    public double getAverageRating(int articleId) {
        Double average = jdbcClient.sql(SQLQueries.SELECT_AVERAGE_RATING_BY_ARTICLE_ID_QUERY)
                .param(1, articleId)
                .query(Double.class)
                .optional()
                .orElse(0.0);
        return Math.round(average * 100.0) / 100.0;
    }

}
