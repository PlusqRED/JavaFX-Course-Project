package ru.grape.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate {
    private Long id;
    private Client client;
    private Integer variety;
    private Integer satisfaction;
    private Integer exercise_time;
    private Integer trainers_time;
    private Integer quality;
}
