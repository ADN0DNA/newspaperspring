package org.example.newspaperspring.domain.mappers;



import org.example.newspaperspring.dao.model.TypeEntity;
import org.example.newspaperspring.domain.model.TypeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeMapperService {

    public List<TypeDTO> mapToDTOs(List <TypeEntity> types) {
        List<TypeDTO> typeDTOs = new java.util.ArrayList<>();
        for (TypeEntity type : types) {
            typeDTOs.add(mapToDTO(type));
        }
        return typeDTOs;
    }

    private TypeDTO mapToDTO(TypeEntity type) {
        return new TypeDTO(
            type.getId(),
            type.getDescription()
        );
    }
}
