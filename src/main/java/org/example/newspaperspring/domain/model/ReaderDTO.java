package org.example.newspaperspring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDTO {
    private int idReader;
    private String nameReader;
    private LocalDate dobReader;
}
