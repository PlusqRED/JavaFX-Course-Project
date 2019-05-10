package ru.grape.course.controllers;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONObject;
import ru.grape.course.controllers.commons.DaoAction;
import ru.grape.course.controllers.commons.Session;
import ru.grape.course.controllers.utils.DialogPopup;
import ru.grape.course.controllers.utils.ServerSender;
import ru.grape.course.model.Account;
import ru.grape.course.model.Client;

import java.io.IOException;

public class StartPageController {

    private ServerSender serverSender = ServerSender.getInstance();

    private Gson gson = new Gson();

    @FXML
    private JFXTextField login_tf;

    @FXML
    private JFXPasswordField password_tf;

    private Stage stage;

    @FXML
    void signIn() throws IOException {
        String login = login_tf.getText();
        String password = password_tf.getText();

        boolean allOk = true;
        if(login.isEmpty()) {
            login_tf.setText("");
            login_tf.setPromptText("Вы пропустили поле!");
            login_tf.unFocusColorProperty().set(Color.RED);
            allOk = false;
        } else {
            login_tf.unFocusColorProperty().set(Color.BLACK);
        }

        if(password.isEmpty()) {
            password_tf.setText("");
            password_tf.setPromptText("Вы пропустили поле!");
            password_tf.unFocusColorProperty().set(Color.RED);
            allOk = false;
        } else {
            password_tf.unFocusColorProperty().set(Color.BLACK);
        }

        if(allOk) {
            JSONObject object = new JSONObject();
            object.put("login", login);
            serverSender.send(object, DaoAction.CHECK_IF_LOGIN_EXISTS);
            JSONObject retrieve = serverSender.retrieve();
            Account account = gson.fromJson(retrieve.getString("account"), Account.class);
            if(account == null) {
                DialogPopup.showDialog(stage, "Ошибка!", "Пользователя не существует", 400, 100);
                login_tf.unFocusColorProperty().set(Color.RED);
            } else {
                login_tf.unFocusColorProperty().set(Color.BLACK);
                password_tf.unFocusColorProperty().set(Color.BLACK);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("toHash", password);
                serverSender.send(jsonObject, DaoAction.GIVE_ME_HASH);
                JSONObject hash = serverSender.retrieve();
                if(account.getPassword().equals(hash.getString("hashed"))) {
                    Session.getInstance().setClient(getClientByAccountId(account.getId()));
                    switch (account.getRole()) {
                        case ADMIN:
                            goToAdminMenu();
                            break;
                        case EXPERT:
                            goToExpertMenu();
                            break;
                        case USER:
                            goToClientMenu();
                            break;
                    }
                } else {
                    DialogPopup.showDialog(stage, "Ошибка!", "Неверный пароль", 400, 100);
                    password_tf.unFocusColorProperty().set(Color.RED);
                }
            }
        }
    }

    private void goToExpertMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expert_page.fxml"));
        Parent root = loader.load();
        ExpertPageController controller = loader.getController();
        controller.setStage(stage);
        JFXDecorator decorator = new JFXDecorator(stage, root);
        stage.setTitle("Меню экспертов");
        stage.setResizable(false);
        stage.setScene(new Scene(decorator));
        stage.showAndWait();
    }

    private void goToAdminMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin_page.fxml"));
        Parent root = loader.load();
        AdminPageController controller = loader.getController();
        controller.setStage(stage);
        JFXDecorator decorator = new JFXDecorator(stage, root);
        stage.setTitle("Меню администратора");
        stage.setResizable(false);
        stage.setScene(new Scene(decorator));
        stage.showAndWait();
    }

    private Client getClientByAccountId(Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        serverSender.send(jsonObject, DaoAction.GET_CLIENT_BY_ACCOUNT_ID);
        JSONObject inputObject = serverSender.retrieve();
        return gson.fromJson(inputObject.getString("client"), Client.class);
    }

    private void goToClientMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_page.fxml"));
        Parent root = loader.load();
        MainPageController controller = loader.getController();
        controller.setStage(stage);
        JFXDecorator decorator = new JFXDecorator(stage, root);
        stage.setTitle("Главное меню");
        stage.setResizable(false);
        stage.setScene(new Scene(decorator));
        stage.showAndWait();
    }

    @FXML
    void signUp() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sign_up_page.fxml"));
        Parent root = loader.load();
        SignUpPageController controller = loader.getController();
        controller.setStage(stage);
        JFXDecorator decorator = new JFXDecorator(stage, root);
        stage.setTitle("Регистрация");
        stage.setResizable(false);
        stage.setScene(new Scene(decorator));
        stage.showAndWait();
    }

    public void shutdown() {

    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
}
