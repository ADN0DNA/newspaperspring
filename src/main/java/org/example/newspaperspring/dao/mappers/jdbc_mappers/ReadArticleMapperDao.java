package org.example.newspaperspring.dao.mappers.jdbc_mappers;

import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReadArticleMapperDao {
    public List<ReadArticleEntity> mapReadArticles(ResultSet rs) throws SQLException {
        List<ReadArticleEntity> list = new java.util.ArrayList<>();
        while (rs.next()) {
            ReadArticleEntity readArticle = new ReadArticleEntity();
            readArticle.setId(rs.getInt("id"));
            readArticle.setArticleId(rs.getInt("article_id"));
            readArticle.setReaderId(rs.getInt("reader_id"));
            readArticle.setRating(rs.getInt("rating"));

            list.add(readArticle);
        }
        return list;
    }

    public ReadArticleEntity mapReadArticle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            ReadArticleEntity readArticle = new ReadArticleEntity();
            readArticle.setId(rs.getInt("id"));
            readArticle.setArticleId(rs.getInt("article_id"));
            readArticle.setReaderId(rs.getInt("reader_id"));
            readArticle.setRating(rs.getInt("rating"));
            return readArticle;
        }
        return null;
    }
}
