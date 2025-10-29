package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.CredentialRepository;
import org.example.newspaperspring.dao.model.CredentialEntity;
import org.example.newspaperspring.domain.model.CredentialDTO;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;


    public CredentialService(CredentialRepository credentialRepository){
        this.credentialRepository = credentialRepository;
    }

    public boolean checkLogin(CredentialDTO credentialDTO){
        CredentialEntity credentialEntity= credentialRepository.get(credentialDTO.getUsername()); //We create an entity that will be whatever the DAO returns. We then call the DB with a username and compare it with its password if both are correct return true

        return credentialEntity.getPassword().equals(credentialDTO.getPassword());
    }
}
