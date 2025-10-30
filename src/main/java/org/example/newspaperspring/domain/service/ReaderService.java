package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.NewspaperRepository;
import org.example.newspaperspring.dao.ReadArticleRepository;
import org.example.newspaperspring.dao.ReaderRepository;
import org.example.newspaperspring.dao.jdbc.jdbcReaderRepository;
import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.example.newspaperspring.dao.model.ReadArticleEntity;
import org.example.newspaperspring.dao.model.ReaderEntity;
import org.example.newspaperspring.domain.mappers.ReadArticleMapperService;
import org.example.newspaperspring.domain.mappers.ReaderMapperService;
import org.example.newspaperspring.domain.model.ReadArticleDTO;
import org.example.newspaperspring.domain.model.ReaderDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final jdbcReaderRepository jdbcReaderRepository;
    private final ReaderMapperService readerMapperService;
    private final ReadArticleRepository readArticleRepository;
    private final ReadArticleMapperService readArticleMapperService;
    private final NewspaperRepository newspaperRepository;

    public ReaderService(ReaderRepository readerRepository, jdbcReaderRepository jdbcReaderRepository, ReaderMapperService readerMapperService, ReadArticleRepository readArticleRepository, ReadArticleMapperService readArticleMapperService, NewspaperRepository newspaperRepository) {
        this.readerRepository = readerRepository;
        this.jdbcReaderRepository = jdbcReaderRepository;
        this.readerMapperService = readerMapperService;
        this.readArticleRepository = readArticleRepository;
        this.readArticleMapperService = readArticleMapperService;
        this.newspaperRepository = newspaperRepository;
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

        List <ReadArticleDTO> readArticleDTOS = readArticleMapperService.mapToDTOs(readArticleRepository.getAllByArticleId(articleId));

        for (ReadArticleDTO readArticle : readArticleDTOS) {

            ReaderEntity reader = readerRepository.get(readArticle.getIdReader());

                readArticle.setNameReader(reader.getName());
                readArticle.setDobReader(reader.getDob());

            List <String> newspaperNames = new ArrayList<>();

            List<NewspaperEntity> newspaperEntities = newspaperRepository.getAllByReader(readArticle.getIdReader());

            newspaperEntities.forEach(newspaper -> {
                newspaperNames.add(newspaper.getName());
            });

            readArticle.setRating(readArticleRepository.get(readArticle.getIdReader()).getRating());
            readArticle.setSubscriptionsReader(newspaperNames);


        }


        return readArticleDTOS;

    }
}
