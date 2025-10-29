package org.example.newspaperspring.ui;


import org.example.newspaperspring.domain.model.ArticleDTO;
import org.example.newspaperspring.domain.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RestArticle {
    private final ArticleService articleService;
    public RestArticle(ArticleService articleService) {
        this.articleService = articleService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/articles")
    public List<ArticleDTO> getArticles() {
        return articleService.getAllArticles();
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/articles")
    public int addArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.add(articleDTO);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/articles")
    public void updateArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.update(articleDTO);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/articles/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable int articleId, @RequestParam(required = false) boolean confirm) {
        articleService.deleteArticle(articleId,confirm);
    }
}