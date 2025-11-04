package org.example.newspaperspring.dao.mappers.spring_mappers;

import org.example.newspaperspring.dao.model.TypeEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TypeRowMapper implements RowMapper<TypeEntity> {
    @Override
    public TypeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(rs.getInt("id"));
        typeEntity.setDescription(rs.getString("description"));
        return typeEntity;
    }
}

