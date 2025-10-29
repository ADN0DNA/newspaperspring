package org.example.newspaperspring.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderEntity {
    private int id;
    private String name;
    private LocalDate dob;
}
