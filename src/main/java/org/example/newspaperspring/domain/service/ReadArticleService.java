package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.ReadArticleRepository;
import org.example.newspaperspring.dao.ReaderRepository;
import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.example.newspaperspring.dao.model.ReaderEntity;
import org.example.newspaperspring.domain.mappers.ReadArticleMapperService;
import org.example.newspaperspring.domain.model.ReadArticleDTO;
import org.example.newspaperspring.domain.model.ReaderDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadArticleService {
    private final ReadArticleRepository readArticleRepository;
    private final ReaderRepository readerRepository;
    private final ReadArticleMapperService readArticleMapperService;

    public ReadArticleService(ReadArticleRepository readArticleRepository, ReaderRepository readerRepository, ReadArticleMapperService readArticleMapperService) {
        this.readArticleRepository = readArticleRepository;
        this.readerRepository = readerRepository;
        this.readArticleMapperService = readArticleMapperService;
    }

    public void deleteByArticleId(int articleId) {
        readArticleRepository.deleteByArticleId(articleId);
    }

    public List<ReaderDTO> getReadersByArticleId(int articleId) {
        List<ReadArticleEntity> allReadArticles = readArticleRepository.getAll();
        List<Integer> readerIds = new ArrayList<>();
        for (int i = 0; i < allReadArticles.size(); i++) {
            ReadArticleEntity ra = allReadArticles.get(i);
            if (ra.getArticleId() == articleId) {
                int readerId = ra.getReaderId();
                boolean alreadyAdded = false;
                for (int j = 0; j < readerIds.size(); j++) {
                    if (readerIds.get(j) == readerId) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    readerIds.add(readerId);
                }
            }
        }

        List<ReaderDTO> readers = new ArrayList<>();
        for (int i = 0; i < readerIds.size(); i++) {
            ReaderEntity entity = readerRepository.get(readerIds.get(i));
            if (entity != null) {
                readers.add(new ReaderDTO(entity.getId(), entity.getName(), entity.getDob()));
            }
        }
        return readers;
    }

    public void addRating(int articleId, int readerId, int rating) {
        List<ReadArticleEntity> all = readArticleRepository.getAll();
        for (int i = 0; i < all.size(); i++) {
            ReadArticleEntity ra = all.get(i);
            if (ra.getArticleId() == articleId && ra.getReaderId() == readerId) {
                // Already exists, do not add
                return;
            }
        }
        ReadArticleEntity entity = new ReadArticleEntity();
        entity.setArticleId(articleId);
        entity.setReaderId(readerId);
        entity.setRating(rating);
        readArticleRepository.save(entity);
    }

    public void updateRating(int articleId, int readerId, int rating) {
        List<ReadArticleEntity> all = readArticleRepository.getAll();
        for (int i = 0; i < all.size(); i++) {
            ReadArticleEntity ra = all.get(i);
            if (ra.getArticleId() == articleId && ra.getReaderId() == readerId) {
                ra.setRating(rating);
                readArticleRepository.update(ra);
                return;
            }
        }
    }

    public void deleteRating(int articleId, int readerId) {
        List<ReadArticleEntity> all = readArticleRepository.getAll();
        for (int i = 0; i < all.size(); i++) {
            ReadArticleEntity ra = all.get(i);
            if (ra.getArticleId() == articleId && ra.getReaderId() == readerId) {
                readArticleRepository.delete(ra);
                return;
            }
        }
    }

    public List<ReadArticleDTO> getAllReadArticles() {
        List<ReadArticleEntity> readArticles = readArticleRepository.getAll();
        List<ReadArticleDTO> result = new ArrayList<>();
        for (ReadArticleEntity entity : readArticles) {
            result.add(readArticleMapperService.mapToDTO(entity));
        }
        return result;
    }

    public ReadArticleDTO get(int id) {
        ReadArticleEntity entity = readArticleRepository.get(id);
        if (entity == null) return null;
        return readArticleMapperService.mapToDTO(entity);
    }

    public int addReadArticle(ReadArticleDTO readArticleDTO) {
        ReadArticleEntity entity = readArticleMapperService.mapToEntity(readArticleDTO);

        readArticleRepository.save(entity);
        return entity.getId();
    }

    public boolean delete(ReadArticleDTO readArticleDTO) {
        ReadArticleEntity entity = readArticleMapperService.mapToEntity(readArticleDTO);
        readArticleRepository.delete(entity);
        return true;
    }

    public void update(ReadArticleDTO readArticleDTO) {
        ReadArticleEntity entity = readArticleMapperService.mapToEntity(readArticleDTO);
        readArticleRepository.update(entity);
    }
}
