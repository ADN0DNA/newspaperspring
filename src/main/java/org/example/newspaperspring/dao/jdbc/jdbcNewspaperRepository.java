package org.example.newspaperspring.dao.jdbc;

import org.example.newspaperspring.dao.NewspaperRepository;
import org.example.newspaperspring.dao.mappers.jdbc_mappers.NewspaperMapperDao;
import org.example.newspaperspring.dao.model.NewspaperEntity;
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
public class jdbcNewspaperRepository implements NewspaperRepository {

    private final SQLQueries sqlQueries;
    private final DBConnectionPool dbConnectionPool;
    private final NewspaperMapperDao newspaperMapperDao;


    public jdbcNewspaperRepository(SQLQueries sqlQueries, DBConnectionPool dbConnectionPool, NewspaperMapperDao newspaperMapperDao) {
        this.sqlQueries = sqlQueries;
        this.dbConnectionPool = dbConnectionPool;
        this.newspaperMapperDao = newspaperMapperDao;
    }

    @Override
    public List<NewspaperEntity> getAll() {
        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_NEWSPAPERS_QUERY);
            return newspaperMapperDao.mapNewspapers(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }
    }

    @Override
    public NewspaperEntity get(int id) {
        return null;
    }

    @Override
    public int save(NewspaperEntity newspaper) {
        return 0;
    }

    @Override
    public void update(NewspaperEntity newspaper) {

    }

    @Override
    public void delete(NewspaperEntity newspaper) {

    }

    @Override
    public List<NewspaperEntity> getAllByReader(int readerId) {
        try (Connection con = dbConnectionPool.getConnection();
             var stmt = con.prepareStatement(SQLQueries.SELECT_NEWSPAPERS_BY_READER_QUERY)) {
            stmt.setInt(1, readerId);
            ResultSet rs = stmt.executeQuery();
            return newspaperMapperDao.mapNewspapers(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());
        }
    }
}
