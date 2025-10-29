package org.example.newspaperspring.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionEntity {
    private int readerId;
    private int newspaperId;
    private LocalDate startDate;
    private LocalDate endDate;
}
