package org.example.newspaperspring.ui;


import org.example.newspaperspring.domain.model.CredentialDTO;
import org.example.newspaperspring.domain.service.CredentialService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCredential {

    private final CredentialService credentialService;

    public RestCredential(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public boolean login(@RequestBody CredentialDTO userCredentialsUI) {
        return credentialService.checkLogin(userCredentialsUI);
    }
}