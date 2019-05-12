package ru.grape.course.controllers.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DialogPopup {

    private DialogPopup() {
    }

    public static void showDialog(Stage stage, String headingText, String bodyText, double dialogWidth, double dialogHeight) {
        JFXDecorator pane = (JFXDecorator) stage.getScene().getRoot();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(headingText));
        content.setBody(new Text(bodyText));
        content.setPrefSize(dialogWidth, dialogHeight);
        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER, true);
        JFXButton button = new JFXButton("Ок");
        button.setOnAction(event -> dialog.close());
        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setPrefHeight(32);
        content.setActions(button);
        pane.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (pane.getHeight() - content.getPrefHeight()) / 2);
        AnchorPane.setLeftAnchor(stackPane, (pane.getWidth() - content.getPrefWidth()) / 2);
        dialog.show();
    }
}
