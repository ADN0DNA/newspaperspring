package org.example.newspaperspring.domain.mappers;

import org.example.newspaperspring.dao.model.ArticleEntity;
import org.example.newspaperspring.dao.model.TypeEntity;
import org.example.newspaperspring.domain.model.ArticleDTO;
import org.example.newspaperspring.domain.model.TypeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleMapperService {

    public List<ArticleDTO> mapToDTOs(List<ArticleEntity> articles, List<Double> avgRatings) {
        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (int i = 0; i < articles.size(); i++) {
            articleDTOs.add(mapToDTO(articles.get(i), avgRatings.get(i)));
        }
        return articleDTOs;
    }

    public ArticleDTO mapToDTO(ArticleEntity article, double avgRating) {
        TypeDTO typeDTO = null;
        if (article.getType() != null) {
            typeDTO = new TypeDTO(
                    article.getType().getId(),
                    article.getType().getDescription()
            );
        }
        return new ArticleDTO(
                article.getId(),
                article.getName(),
                typeDTO,
                article.getNPaperId(),
                avgRating
        );
    }

    public ArticleEntity mapToEntity(ArticleDTO articleDTO) {
        TypeEntity typeEntity = null;
        if (articleDTO.getTypeUI() != null) {
            typeEntity = new TypeEntity(
                    articleDTO.getTypeUI().getId(),
                    articleDTO.getTypeUI().getName()
            );
        }
        return new ArticleEntity(
                articleDTO.getId(),
                articleDTO.getName(),
                typeEntity,
                articleDTO.getNpaperId()
        );
    }
}
