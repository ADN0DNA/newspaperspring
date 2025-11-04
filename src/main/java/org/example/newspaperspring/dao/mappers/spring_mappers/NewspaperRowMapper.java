package org.example.newspaperspring.dao.mappers.spring_mappers;

import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NewspaperRowMapper implements RowMapper<NewspaperEntity> {
    @Override
    public NewspaperEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        NewspaperEntity newspaperEntity = new NewspaperEntity();
        newspaperEntity.setId(rs.getInt("id"));
        newspaperEntity.setName(rs.getString("name"));
        java.sql.Date releaseDate = rs.getDate("release_date");
        if (releaseDate != null) newspaperEntity.setReleaseDate(releaseDate.toLocalDate());
        return newspaperEntity;
    }
}

