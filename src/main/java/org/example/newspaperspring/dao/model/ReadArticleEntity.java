package org.example.newspaperspring.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadArticleEntity {
    private int id;
    private int articleId;
    private int readerId;
    private int rating;

}
