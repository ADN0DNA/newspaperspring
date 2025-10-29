package org.example.newspaperspring.dao;


import org.example.newspaperspring.dao.model.CredentialEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialRepository {

    List<CredentialEntity> getAll();

    CredentialEntity get(String username);

    int save(CredentialEntity credential);

    void update(CredentialEntity credential);

    void delete(CredentialEntity credential);

}
