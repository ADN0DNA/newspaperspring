package org.example.newspaperspring.dao.mappers.jdbc_mappers;



import org.example.newspaperspring.dao.model.ReaderEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReaderMapperDao {
    public List<ReaderEntity> mapReaders(ResultSet rs) throws SQLException {
        List<ReaderEntity> list = new ArrayList<>();
        while (rs.next()) {
            ReaderEntity reader = new ReaderEntity();
            reader.setId(rs.getInt("id"));
            reader.setName(rs.getString("name"));
            reader.setDob(rs.getDate("dob").toLocalDate());


            list.add(reader);
        }
        return list;
    }

    public ReaderEntity mapReader(ResultSet rs) throws SQLException {
        if (rs.next()) {
            ReaderEntity reader = new ReaderEntity();
            reader.setId(rs.getInt("id"));
            reader.setName(rs.getString("name"));
            reader.setDob(rs.getDate("dob").toLocalDate());
            return reader;
        }
        return null;
    }
}
