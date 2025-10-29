package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.ReadArticleRepository;
import org.example.newspaperspring.dao.ReaderRepository;
import org.example.newspaperspring.dao.jdbc.jdbcReaderRepository;
import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.example.newspaperspring.dao.model.ReaderEntity;
import org.example.newspaperspring.domain.mappers.ReaderMapperService;
import org.example.newspaperspring.domain.model.ReadArticleDTO;
import org.example.newspaperspring.domain.model.ReaderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final jdbcReaderRepository jdbcReaderRepository;
    private final ReaderMapperService readerMapperService;
    private final ReadArticleRepository readArticleRepository;

    public ReaderService(ReaderRepository readerRepository, jdbcReaderRepository jdbcReaderRepository, ReaderMapperService readerMapperService, ReadArticleRepository readArticleRepository) {
        this.readerRepository = readerRepository;
        this.jdbcReaderRepository = jdbcReaderRepository;
        this.readerMapperService = readerMapperService;
        this.readArticleRepository = readArticleRepository;
    }

    public List<ReaderDTO> getAllReaders() {
        List<ReaderEntity> readers = readerRepository.getAll();
        return readerMapperService.mapToDTOs(readers);
    }

    public int addReader(ReaderDTO reader) {
        ReaderEntity entity = readerMapperService.mapToEntity(reader);
        return readerRepository.save(entity);
    }

    public void addCredentials(String username, String password, int readerId) {
        jdbcReaderRepository.saveCredentials(username, password, readerId);
    }

    public void deleteReader(int readerId, boolean deleteCredentials) {
        ReaderEntity reader = readerRepository.get(readerId);
        if (deleteCredentials) {
            jdbcReaderRepository.deleteCredentialsByReaderId(readerId);
        }
        readerRepository.delete(reader);
    }

    public ReaderDTO get(int id) {
        ReaderEntity entity = readerRepository.get(id);
        if (entity == null) {
            return null;
        }
        return readerMapperService.mapToDTO(entity);
    }

    public List<ReadArticleDTO> getAllReadersByArticleId(int articleId) {
        List<ReaderEntity> readers = readerRepository.getAll();
        List<ReadArticleEntity> readArticles = readArticleRepository.getAll();
        List<ReaderDTO> articleReaders = null;
        for (ReadArticleEntity readArticle : readArticles) {
            if (readArticle.getArticleId() == articleId) {
                for (ReaderEntity reader : readers) {
                    if (reader.getId() == readArticle.getReaderId()) {
                        articleReaders.add(readerMapperService.mapToDTO(reader));
                    }
                }
            }

        }

        //return articleReaders;
        System.out.println("DELETE LATER, IF THIS METHOD IS SUPPOSED TO RETURN ALL THE READERS OF AN ARTICLE, WHY IS IT RETURNING List<ReadArticleDTO> THOSE ARE NOT THE READERS");
        return null;
    }
}
