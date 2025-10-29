package org.example.newspaperspring.dao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NewspaperEntity {
    @Id
    int id;
    String name;
    LocalDate releaseDate;
}
