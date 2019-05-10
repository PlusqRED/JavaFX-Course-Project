package ru.grape.course.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

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
