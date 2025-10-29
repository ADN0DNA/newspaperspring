package org.example.newspaperspring.dao.jdbc;

import org.example.newspaperspring.dao.TypeRepository;
import org.example.newspaperspring.dao.mappers.jdbc_mappers.TypeMapperDao;
import org.example.newspaperspring.dao.model.TypeEntity;
import org.example.newspaperspring.dao.utils.DBConnectionPool;
import org.example.newspaperspring.dao.utils.SQLQueries;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.error.DatabaseError;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Profile("jdbc")
@Repository
public class jdbcTypeRepository implements TypeRepository {

    private final TypeMapperDao typeMapperDao;
    private final SQLQueries sqlQueries;
    private final DBConnectionPool dbConnectionPool;

    public jdbcTypeRepository(TypeMapperDao typeMapperDao, SQLQueries sqlQueries,
                              DBConnectionPool dbConnectionPool) {
        this.typeMapperDao = typeMapperDao;
        this.sqlQueries = sqlQueries;
        this.dbConnectionPool = dbConnectionPool;
    }


    @Override
    public List<TypeEntity> getAll() {
        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_TYPES_QUERY);
            return typeMapperDao.mapTypes(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }
    }

    @Override
    public TypeEntity get(int id) {
        return null;
    }

    @Override
    public int save(TypeEntity type) {
        return 0;
    }

    @Override
    public void update(TypeEntity type) {

    }

    @Override
    public void delete(TypeEntity type) {

    }
}
