package org.example.newspaperspring.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialEntity {
    private String username;
    private String password;
    private int idReader;
}
