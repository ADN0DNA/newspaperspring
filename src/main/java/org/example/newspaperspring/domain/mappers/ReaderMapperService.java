package org.example.newspaperspring.domain.mappers;

import org.example.newspaperspring.dao.model.ReaderEntity;
import org.example.newspaperspring.domain.model.ReaderDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderMapperService {
    public List<ReaderDTO> mapToDTOs(List<ReaderEntity> readers) {
        List<ReaderDTO> readerDTOs = new ArrayList<>();
        for (ReaderEntity reader : readers) {
            readerDTOs.add(mapToDTO(reader));
        }
        return readerDTOs;
    }

    public ReaderDTO mapToDTO(ReaderEntity reader) {
        return new ReaderDTO(
                reader.getId(),
                reader.getName(),
                reader.getDob()
        );
    }

    public ReaderEntity mapToEntity(ReaderDTO readerDTO) {
        return new ReaderEntity(
                readerDTO.getIdReader(),
                readerDTO.getNameReader(),
                readerDTO.getDobReader()
        );
    }
}
