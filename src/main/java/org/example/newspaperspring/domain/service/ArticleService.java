// java
package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.ArticleRepository;
import org.example.newspaperspring.dao.model.ArticleEntity;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.mappers.ArticleMapperService;
import org.example.newspaperspring.domain.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapperService articleMapperService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          ArticleMapperService articleMapperService) {
        this.articleRepository = articleRepository;
        this.articleMapperService = articleMapperService;
    }

    public List<ArticleDTO> getAllArticles() {
        List<ArticleEntity> articles = articleRepository.getAll();
        List<Double> avgRatings = new ArrayList<>();

        for (ArticleEntity article : articles) {
            avgRatings.add(articleRepository.getAverageRating(article.getId()));
        }

        return articleMapperService.mapToDTOs(articles, avgRatings);
    }

    public ArticleDTO get(int id) {
        ArticleEntity article = articleRepository.get(id);
        if (article == null) {
            throw new AppError("Article not found with id: " + id);
        }
        double avgRating = articleRepository.getAverageRating(id);
        return articleMapperService.mapToDTO(article, avgRating);
    }


    public int add(ArticleDTO articleDTO) {
        ArticleEntity article = articleMapperService.mapToEntity(articleDTO);
        articleRepository.save(article);
        return article.getId();
    }

    public void update(ArticleDTO articleDTO) {
        ArticleEntity article = articleMapperService.mapToEntity(articleDTO);
        articleRepository.update(article);
    }

    public void deleteArticle(int i, boolean b) {
        articleRepository.delete(i, b);
    }
}
