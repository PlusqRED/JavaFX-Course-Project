package ru.grape.course.controllers;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONObject;
import ru.grape.course.controllers.commons.DaoAction;
import ru.grape.course.controllers.utils.DialogPopup;
import ru.grape.course.controllers.utils.ServerSender;
import ru.grape.course.model.Account;
import ru.grape.course.model.Client;
import ru.grape.course.model.Role;

import java.time.LocalDate;

public class SignUpPageController {
    private final ServerSender serverSender = ServerSender.getInstance();
    private Stage stage;
    private final Gson gson = new Gson();

    @FXML
    private JFXTextField profile_name;

    @FXML
    private JFXTextField profile_surname;

    @FXML
    private JFXTextField profile_city;

    @FXML
    private JFXTextField profile_phone;

    @FXML
    private JFXTextField profile_weight;

    @FXML
    private JFXTextField profile_height;

    @FXML
    private JFXDatePicker profile_birthday;

    @FXML
    private JFXTextField profile_login;

    @FXML
    private JFXTextField profile_password;

    @FXML
    public void signUp() {
        boolean allOk = true;
        Client.ClientBuilder clientBuilder = Client.builder();
        Account.AccountBuilder accountBuilder = Account.builder();
        if (profile_login.getText().isEmpty()) {
            allOk = false;
            profile_login.setUnFocusColor(Color.RED);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", profile_login.getText());
            serverSender.send(jsonObject, DaoAction.CHECK_IF_LOGIN_EXISTS);
            JSONObject retrieve = serverSender.retrieve();
            Account accountFromServer = gson.fromJson(retrieve.getString("account"), Account.class);
            if (accountFromServer != null) {
                allOk = false;
                profile_login.setUnFocusColor(Color.RED);
                DialogPopup.showDialog(stage, "Ошибка!", "Данный логин уже занят, попробуйте другой.", 400, 200);
            } else {
                accountBuilder.login(profile_login.getText());
                profile_login.setUnFocusColor(Color.BLACK);
            }
        }
        if (profile_password.getText().isEmpty()) {
            allOk = false;
            profile_password.setUnFocusColor(Color.RED);
        } else {
            profile_password.setUnFocusColor(Color.BLACK);
            accountBuilder.password(profile_password.getText());
        }
        String doublePattern = "^[0-9][.[0-9]]*";
        if (!profile_weight.getText().isEmpty()) {
            if (!profile_weight.getText().matches(doublePattern)) {
                allOk = false;
                profile_weight.setUnFocusColor(Color.RED);
            } else {
                profile_weight.setUnFocusColor(Color.BLACK);
                clientBuilder.weight(Double.valueOf(profile_weight.getText()));
            }
        }
        if (!profile_height.getText().isEmpty()) {
            if (!profile_height.getText().matches(doublePattern)) {
                allOk = false;
                profile_height.setUnFocusColor(Color.RED);
            } else {
                profile_height.setUnFocusColor(Color.BLACK);
                clientBuilder.height(Double.valueOf(profile_height.getText()));
            }
        }
        if (!profile_phone.getText().isEmpty()) {
            if (!profile_phone.getText().matches("[0-9]{12}")) {
                allOk = false;
                profile_phone.setUnFocusColor(Color.RED);
            } else {
                profile_phone.setUnFocusColor(Color.BLACK);
                clientBuilder.phone(Long.valueOf(profile_phone.getText()));
            }
        }
        if (profile_birthday.getValue() == null) {
            allOk = false;
            DialogPopup.showDialog(stage, "Ошибка!", "Необходимо ввести дату рождения", 400, 200);
        } else {
            LocalDate value = profile_birthday.getValue();
            LocalDate check = LocalDate.of(
                    LocalDate.now().getYear() - 1,
                    LocalDate.now().getMonth(),
                    LocalDate.now().getDayOfMonth()
            );
            if (value.isAfter(check)) {
                allOk = false;
                DialogPopup.showDialog(stage, "Ошибка!", "Некорректная дата рождения", 400, 200);
            }
        }
        if (allOk) {
            clientBuilder.birthday(profile_birthday.getValue());
            clientBuilder.city(profile_city.getText().isEmpty() ? null : profile_city.getText());
            clientBuilder.name(profile_name.getText().isEmpty() ? null : profile_name.getText());
            clientBuilder.surname(profile_surname.getText().isEmpty() ? null : profile_surname.getText());
            clientBuilder.account(accountBuilder.role(Role.USER).build());
            Client client = clientBuilder.build();
            JSONObject object = new JSONObject();
            object.put("client", gson.toJson(client));
            serverSender.send(object, DaoAction.ADD_CLIENT);
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
