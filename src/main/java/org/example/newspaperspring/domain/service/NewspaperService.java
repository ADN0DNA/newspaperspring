package org.example.newspaperspring.domain.service;


import org.example.newspaperspring.dao.NewspaperRepository;
import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.example.newspaperspring.domain.mappers.NewspaperMapperService;
import org.example.newspaperspring.domain.model.NewspaperDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewspaperService {

    private final NewspaperRepository newspaperRepository;
    private final NewspaperMapperService newspaperMapperService;


    public NewspaperService (NewspaperRepository newspaperRepository, NewspaperMapperService newspaperMapperService) {
        this.newspaperRepository = newspaperRepository;
        this.newspaperMapperService = newspaperMapperService;

    }

    public List<NewspaperDTO> getAllNewspapers() {
        List<NewspaperEntity> newspapers = newspaperRepository.getAll();
        return newspaperMapperService.mapToDTOs(newspapers);
    }
}
