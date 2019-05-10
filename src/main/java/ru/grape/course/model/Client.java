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
public class Client {
    private Long id;
    private String name;
    private String surname;
    private String city;
    private Long phone;
    private Double weight;
    private Double height;
    private Account account;
    private LocalDate birthday;
}
