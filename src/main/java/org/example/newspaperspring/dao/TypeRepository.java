package org.example.newspaperspring.dao;


import org.example.newspaperspring.dao.model.TypeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository {
    List<TypeEntity> getAll();

    TypeEntity get(int id);

    int save(TypeEntity type);

    void update(TypeEntity type);

    void delete(TypeEntity type);
}
