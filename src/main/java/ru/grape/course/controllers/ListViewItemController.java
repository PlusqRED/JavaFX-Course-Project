package ru.grape.course.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ListViewItemController {
    @FXML
    private ImageView image;

    @FXML
    private JFXTextArea description;

    @FXML
    private Text name;

    @FXML
    private Text price;

    private Integer exerciseId;

    public void setImage(Image image) {
        this.image.setImage(image);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setPrice(String price) {
        this.price.setText(price);
    }
}
