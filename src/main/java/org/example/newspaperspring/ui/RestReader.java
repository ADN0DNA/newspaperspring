package org.example.newspaperspring.ui;


import org.example.newspaperspring.domain.model.ReadArticleDTO;
import org.example.newspaperspring.domain.model.ReaderDTO;
import org.example.newspaperspring.domain.service.ReadArticleService;
import org.example.newspaperspring.domain.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RestReader {
    private final ReaderService readerService;
    private final ReadArticleService readArticleService;

    public RestReader(ReaderService readerService, ReadArticleService readArticleService) {
        this.readerService = readerService;
        this.readArticleService = readArticleService;
    }

    @GetMapping("/readers")
    public List<ReaderDTO> getAll() {
        return readerService.getAllReaders();
    }
    @GetMapping("/articles/{articleId}/readers")
    public List<ReadArticleDTO> getReadersByIdArticle(@PathVariable int articleId) {
        return readerService.getAllReadersByArticleId(articleId);
    }

    @GetMapping("/articles/{readerId}/reader")
    public ReadArticleDTO getReader(@PathVariable int readerId) {
        return readArticleService.get(readerId);
    }

    @PostMapping("/articles/readers")
    public int addReadArticle(@RequestBody ReadArticleDTO readArticleDTO) {
        return readArticleService.addReadArticle(readArticleDTO);

    }

    @DeleteMapping("/articles/readers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteReadArticle(@RequestBody ReadArticleDTO readArticleDTO) {
        return readArticleService.delete(readArticleDTO);
    }

    @PutMapping("/articles/readers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReadArticle(@RequestBody ReadArticleDTO readArticleDTO) {
        readArticleService.update(readArticleDTO);
    }

}
