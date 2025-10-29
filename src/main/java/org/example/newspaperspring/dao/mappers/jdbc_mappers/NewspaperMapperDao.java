package org.example.newspaperspring.dao.mappers.jdbc_mappers;


import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewspaperMapperDao {
    public List<NewspaperEntity> mapNewspapers(ResultSet rs) throws SQLException {
        List<NewspaperEntity> list = new ArrayList<>();
        while (rs.next()) {
            NewspaperEntity newspaper = new NewspaperEntity();
            newspaper.setId(rs.getInt("id"));
            newspaper.setName(rs.getString("name"));
            newspaper.setReleaseDate(rs.getDate("release_date").toLocalDate());

            list.add(newspaper);
        }
        return list;
    }
}
