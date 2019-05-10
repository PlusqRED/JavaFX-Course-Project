package ru.grape.course;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.grape.course.controllers.StartPageController;

public class App extends Application {

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/start_page.fxml"));
        Parent root = loader.load();
        StartPageController controller = loader.getController();
        controller.setStage(primaryStage);
        JFXDecorator decorator = new JFXDecorator(primaryStage, root);
        primaryStage.setOnCloseRequest(event -> controller.shutdown());
        primaryStage.setTitle("Фитнес центр");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(decorator));
        primaryStage.show();
    }
}
