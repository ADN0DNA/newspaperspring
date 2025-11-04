package org.example.newspaperspring.dao.mappers.spring_mappers;

import org.example.newspaperspring.dao.model.ReaderEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReaderRowMapper implements RowMapper<ReaderEntity> {

    @Override
    public ReaderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReaderEntity reader = new ReaderEntity();
        reader.setId(rs.getInt("id"));
        reader.setName(rs.getString("name"));
        reader.setDob(rs.getDate("dob").toLocalDate());
        return reader;
    }
}
