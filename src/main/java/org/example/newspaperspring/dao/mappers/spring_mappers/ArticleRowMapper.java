package org.example.newspaperspring.dao.mappers.spring_mappers;

import org.example.newspaperspring.dao.model.ArticleEntity;
import org.example.newspaperspring.dao.model.TypeEntity;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleRowMapper implements RowMapper<ArticleEntity> {

    @Override
    public ArticleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ArticleEntity(rs.getInt("id"),
                rs.getString("name"),
                new TypeEntity(rs.getInt("type_id"), rs.getString( "description")),
                rs.getInt("newspaper_id"));
    }

}
