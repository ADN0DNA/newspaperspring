package org.example.newspaperspring.domain.mappers;

import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.example.newspaperspring.domain.model.ReadArticleDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadArticleMapperService {


    public List<ReadArticleDTO> mapToDTOs(List<ReadArticleEntity> readArticles) {
        List<ReadArticleDTO> readArticleDTOs = new ArrayList<>();
        for (ReadArticleEntity readArticle : readArticles) {
            readArticleDTOs.add(mapToDTO(readArticle));
        }
        return readArticleDTOs;
    }

    public ReadArticleDTO mapToDTO(ReadArticleEntity readArticle) {
        ReadArticleDTO dto = new ReadArticleDTO();

        dto.setIdArticle(readArticle.getArticleId());
        dto.setIdReader(readArticle.getReaderId());
        dto.setRating(readArticle.getRating());

        return dto;
    }
}
