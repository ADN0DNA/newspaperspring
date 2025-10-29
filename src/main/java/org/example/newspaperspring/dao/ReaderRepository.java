package org.example.newspaperspring.dao;

import org.example.newspaperspring.dao.model.ReaderEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository {
    List<ReaderEntity> getAll();

    ReaderEntity get(int id);

    int save(ReaderEntity reader);

    void update(ReaderEntity reader);

    void delete(ReaderEntity reader);

    void saveCredentials(String username, String password, int readerId);

    void deleteCredentialsByReaderId(int readerId);
}
