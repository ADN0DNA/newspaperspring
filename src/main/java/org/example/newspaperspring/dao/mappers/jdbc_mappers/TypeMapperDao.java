package org.example.newspaperspring.dao.mappers.jdbc_mappers;



import org.example.newspaperspring.dao.model.TypeEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TypeMapperDao {
    public List<TypeEntity> mapTypes(ResultSet rs) throws SQLException {

        List<TypeEntity> list = new ArrayList<>();
            while (rs.next()) {
                TypeEntity type = new TypeEntity();
                type.setId(rs.getInt("id"));
                type.setDescription(rs.getString("description"));

                list.add(type);
            }
            return list;

        }
    public TypeEntity mapType(ResultSet rs) throws SQLException {
        if (rs.next()) {
            TypeEntity type = new TypeEntity();
            type.setId(rs.getInt("id"));
            type.setDescription(rs.getString("name"));
            return type;
        }
        return null;
    }
}
