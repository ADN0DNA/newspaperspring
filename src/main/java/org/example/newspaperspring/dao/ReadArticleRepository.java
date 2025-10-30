package org.example.newspaperspring.dao;


import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadArticleRepository {
    List<ReadArticleEntity> getAll();

    ReadArticleEntity get(int id);

    int save(ReadArticleEntity readArticle);

    void update(ReadArticleEntity readArticle);

    void delete(ReadArticleEntity readArticle);

    void deleteByArticleId(int articleId);

    List <ReadArticleEntity> getAllByArticleId(int articleId);
}
