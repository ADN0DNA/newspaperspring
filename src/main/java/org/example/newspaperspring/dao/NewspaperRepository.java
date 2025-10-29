package org.example.newspaperspring.dao;

import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewspaperRepository {
    List<NewspaperEntity> getAll();

    NewspaperEntity get(int id);

    int save(NewspaperEntity newspaper);

    void update(NewspaperEntity newspaper);

    void delete(NewspaperEntity newspaper);
}
