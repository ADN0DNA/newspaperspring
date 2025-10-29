// java
package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.ArticleRepository;
import org.example.newspaperspring.dao.model.ArticleEntity;
import org.example.newspaperspring.domain.error.AppError;
import org.example.newspaperspring.domain.mappers.ArticleMapperService;
import org.example.newspaperspring.domain.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return articleMapperService.mapToDTOs(articles);
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

    public ArticleDTO get(int id) {
        ArticleEntity article = articleRepository.get(id);
        if (article == null) {
            throw new AppError("Article not found with id: " + id);
        }
        return articleMapperService.mapToDTO(article);
    }

    public void deleteArticle(int i, boolean b) {
        articleRepository.delete(i, b);
    }
}
