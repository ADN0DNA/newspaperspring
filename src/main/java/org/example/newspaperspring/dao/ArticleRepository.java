package org.example.newspaperspring.dao;

import org.example.newspaperspring.dao.model.ArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository {

    List<ArticleEntity> getAll();

    ArticleEntity get(int id);

    int save(ArticleEntity article);

    void update(ArticleEntity article);

    void delete(int id, boolean confirmation);

}
