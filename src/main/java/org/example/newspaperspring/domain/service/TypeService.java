package org.example.newspaperspring.domain.service;

import org.example.newspaperspring.dao.TypeRepository;
import org.example.newspaperspring.domain.mappers.TypeMapperService;
import org.example.newspaperspring.domain.model.TypeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapperService typeMapperService;

    public TypeService(TypeRepository typeRepository, TypeMapperService typeMapperService) {
        this.typeRepository = typeRepository;
        this.typeMapperService = typeMapperService;
    }

    public List<TypeDTO> getAllTypes() {
        List types = typeRepository.getAll();
        return typeMapperService.mapToDTOs(types);
    }
}
