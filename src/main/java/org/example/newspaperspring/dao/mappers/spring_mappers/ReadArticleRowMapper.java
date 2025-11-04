package org.example.newspaperspring.dao.mappers.spring_mappers;

import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReadArticleRowMapper implements RowMapper<ReadArticleEntity> {
    @Override
    public ReadArticleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReadArticleEntity readArticleEntity = new ReadArticleEntity();
        readArticleEntity.setId(rs.getInt("id"));
        readArticleEntity.setArticleId(rs.getInt("article_id"));
        readArticleEntity.setReaderId(rs.getInt("reader_id"));
        readArticleEntity.setRating(rs.getInt("rating"));
        return readArticleEntity;
    }
}

