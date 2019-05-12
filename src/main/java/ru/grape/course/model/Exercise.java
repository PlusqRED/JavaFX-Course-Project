package ru.grape.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {
    private Integer id;
    private String name;
    private String description;
    private Integer min_age;
    private Double price;
    private String image_url;
}
