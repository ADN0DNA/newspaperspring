package org.example.newspaperspring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadArticleDTO {
    private int idArticle;
    private int idReader;
    private String nameReader;
    private LocalDate dobReader;
    private List<String> subscriptionsReader;
    private int rating;
}
