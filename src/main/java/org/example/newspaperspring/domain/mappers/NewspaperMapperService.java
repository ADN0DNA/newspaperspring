package org.example.newspaperspring.domain.mappers;


import org.example.newspaperspring.dao.model.NewspaperEntity;
import org.example.newspaperspring.domain.model.NewspaperDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewspaperMapperService {
    public List<NewspaperDTO> mapToDTOs(List<NewspaperEntity> newspapers) {
        List<NewspaperDTO> newspaperDTOs = new ArrayList<>();
        for (NewspaperEntity newspaper : newspapers) {
            newspaperDTOs.add(mapToDTO(newspaper));
        }
        return newspaperDTOs;
    }

    public NewspaperDTO mapToDTO(NewspaperEntity newspaper) {
        return new NewspaperDTO(
            newspaper.getId(),
            newspaper.getName()
        );
    }

    public NewspaperEntity mapToEntity(NewspaperDTO newspaperDTO) {
        return new NewspaperEntity(
            newspaperDTO.getId(),
            newspaperDTO.getName(),
            null // releaseDate is not present in DTO
        );
    }
}
