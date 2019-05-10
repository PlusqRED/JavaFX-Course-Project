package ru.grape.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    private Long id;
    private Client client;
    private Exercise exercise;
    private LocalDate start_date;
}
