package org.example.newspaperspring.dao.springjdbc;

import org.example.newspaperspring.dao.ReadArticleRepository;
import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.example.newspaperspring.dao.mappers.spring_mappers.ReadArticleRowMapper;
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
public class SpringReadArticleRepository implements ReadArticleRepository {

    @Autowired
    private JdbcClient jdbcClient;
    private final ReadArticleRowMapper readArticleRowMapper;

    public SpringReadArticleRepository(JdbcClient jdbcClient, ReadArticleRowMapper readArticleRowMapper) {
        this.jdbcClient = jdbcClient;
        this.readArticleRowMapper = readArticleRowMapper;
    }

    @Override
    public List<ReadArticleEntity> getAll() {
        return jdbcClient.sql(SQLQueries.SELECT_READARTICLES_QUERY)
                .query(readArticleRowMapper)
                .list();
    }

    @Override
    public ReadArticleEntity get(int id) {
        return jdbcClient.sql(SQLQueries.SELECT_READARTICLE_BY_ID_QUERY)
                .param(1, id)
                .query(readArticleRowMapper)
                .optional()
                .orElseThrow(() -> new AppError("ReadArticle not found: " + id));
    }

    @Override
    public int save(ReadArticleEntity readArticle) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(SQLQueries.INSERT_READARTICLE_QUERY)
                .param(1, readArticle.getArticleId())
                .param(2, readArticle.getReaderId())
                .param(3, readArticle.getRating())
                .update(keyHolder);
        return Objects.requireNonNull(keyHolder.getKey(), "ReadArticle ID was not generated").intValue();
    }

    @Override
    public void update(ReadArticleEntity readArticle) {
        jdbcClient.sql(SQLQueries.UPDATE_READARTICLE_QUERY)
                .param(1, readArticle.getRating())
                .param(2, readArticle.getArticleId())
                .param(3, readArticle.getReaderId())
                .update();
    }

    @Override
    public void delete(ReadArticleEntity readArticle) {
        jdbcClient.sql(SQLQueries.DELETE_READARTICLE_QUERY)
                .param(1, readArticle.getReaderId())
                .param(2, readArticle.getArticleId())
                .update();
    }

    @Override
    public void deleteByArticleId(int articleId) {
        jdbcClient.sql(SQLQueries.DELETE_READARTICLE_BY_ARTICLE_ID_QUERY)
                .param(1, articleId)
                .update();
    }

    @Override
    public List<ReadArticleEntity> getAllByArticleId(int articleId) {
        return jdbcClient.sql(SQLQueries.SELECT_READARTICLE_BY_ARTICLE_ID_QUERY)
                .param(1, articleId)
                .query(readArticleRowMapper)
                .list();
    }
}

