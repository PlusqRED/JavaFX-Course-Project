package ru.grape.course.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONObject;
import ru.grape.course.controllers.commons.DaoAction;
import ru.grape.course.controllers.commons.Session;
import ru.grape.course.controllers.utils.DialogPopup;
import ru.grape.course.controllers.utils.ServerSender;
import ru.grape.course.model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPageController {
    private final ServerSender serverSender = ServerSender.getInstance();
    private final Gson gson = new Gson();
    @FXML
    private ImageView all_service_imageView;

    @FXML
    private JFXTextArea all_service_descriprion;

    @FXML
    private TableView<Exercise> services_tv;

    @FXML
    private JFXListView<Parent> my_services_lv;

    @FXML
    private JFXSlider review_variety;

    @FXML
    private JFXSlider review_satisfaction;

    @FXML
    private JFXSlider review_exercise_time;

    @FXML
    private JFXSlider review_trainers_time;

    @FXML
    private JFXSlider review_quality;

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
    private JFXTextField profile_login;

    private Stage stage;

    @FXML
    public void add_to_my_services() {
        Exercise selectedItem = services_tv.getSelectionModel().getSelectedItem();
        Service service = Service.builder()
                .client(Session.getInstance().getClient())
                .exercise(selectedItem)
                .start_date(LocalDate.now())
                .build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("service", gson.toJson(service));
        serverSender.send(jsonObject, DaoAction.ADD_CLIENT_SERVICE);
        loadClientServices();
        DialogPopup.showDialog(stage, "Успех!", selectedItem.getName() + " успешно добавлен в Ваши сервисы", 400, 200);
    }

    @FXML
    public void profile_update() {
        boolean allOk = true;
        Account account = Session.getInstance().getClient().getAccount();

        String doublePattern = "^[0-9][.[0-9]]*";
        if (!profile_weight.getText().matches(doublePattern)) {
            allOk = false;
            profile_weight.setUnFocusColor(Color.RED);
        } else {
            profile_weight.setUnFocusColor(Color.BLACK);
        }
        if (!profile_height.getText().matches(doublePattern)) {
            allOk = false;
            profile_height.setUnFocusColor(Color.RED);
        } else {
            profile_height.setUnFocusColor(Color.BLACK);
        }
        if (!profile_phone.getText().matches("[0-9]{12}")) {
            allOk = false;
            profile_phone.setUnFocusColor(Color.RED);
        } else {
            profile_phone.setUnFocusColor(Color.BLACK);
        }
        if (allOk) {
            Client client = Client.builder()
                    .name(profile_name.getText().trim())
                    .surname(profile_surname.getText().trim())
                    .city(profile_city.getText())
                    .account(Account.builder()
                            .id(account.getId())
                            .login(profile_login.getText().trim())
                            .build())
                    .height(Double.valueOf(profile_height.getText().trim()))
                    .weight(Double.valueOf(profile_weight.getText().trim()))
                    .phone(Long.valueOf(profile_phone.getText().trim()))
                    .id(Session.getInstance().getClient().getId())
                    .build();
            JSONObject object = new JSONObject();
            object.put("client", gson.toJson(client));
            serverSender.send(object, DaoAction.UPDATE_CLIENT);
            DialogPopup.showDialog(stage, "Успех!", "Данные вашего профиля успешно обновлены", 400, 200);
            client.getAccount().setRole(Session.getInstance().getClient().getAccount().getRole());
            Session.getInstance().setClient(client);
        }

    }

    @FXML
    public void review_send() {
        Rate rate = Rate.builder()
                .client(Session.getInstance().getClient())
                .variety((int) Math.round(review_variety.getValue()))
                .satisfaction((int) Math.round(review_satisfaction.getValue()))
                .exercise_time((int) Math.round(review_exercise_time.getValue()))
                .trainers_time((int) Math.round(review_trainers_time.getValue()))
                .quality((int) Math.round(review_quality.getValue()))
                .build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rate", gson.toJson(rate));
        serverSender.send(jsonObject, DaoAction.UPDATE_RATE_BY_CLIENT_ID);
        DialogPopup.showDialog(stage, "Успех!", "Спасибо за Ваш отзыв :)", 400, 200);
    }

    public void initialize() {
        loadRates();
        loadAllServices();
        loadClientServices();
        loadProfile();
    }

    private void loadRates() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", Session.getInstance().getClient().getId());
        serverSender.send(jsonObject, DaoAction.GET_RATE_BY_CLIENT_ID);
        JSONObject retrieve = serverSender.retrieve();
        Rate rate = gson.fromJson(retrieve.getString("rate"), Rate.class);
        if (rate != null) {
            review_variety.setValue(rate.getVariety());
            review_satisfaction.setValue(rate.getSatisfaction());
            review_exercise_time.setValue(rate.getExercise_time());
            review_trainers_time.setValue(rate.getTrainers_time());
            review_quality.setValue(rate.getQuality());
        }
    }

    private void loadAllServices() {
        services_tv.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        services_tv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("min_age"));
        services_tv.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        List<Exercise> exercises = loadExercises();
        services_tv.getItems().addAll(exercises);
        services_tv.setOnMouseClicked(e -> {
            Exercise exercise;
            if ((exercise = services_tv.getSelectionModel().getSelectedItem()) != null) {
                all_service_descriprion.setText(exercise.getDescription());
                all_service_imageView.setImage(new Image("/images/" + exercise.getImage_url()));
            }
        });
    }

    private void loadClientServices() {
        JSONObject outputJson = new JSONObject();
        outputJson.put("id", Session.getInstance().getClient().getId());
        serverSender.send(outputJson, DaoAction.GET_SERVICES_BY_CLIENT_ID);
        JSONObject clientServicesJson = serverSender.retrieve();
        Type servicesList = new TypeToken<ArrayList<Service>>() {
        }.getType();
        my_services_lv.getItems().clear();
        List<Service> services = gson.fromJson(clientServicesJson.getString("services"), servicesList);
        services.forEach(e -> {
            ImageView imageView = new ImageView("/images/" + e.getExercise().getImage_url());
            imageView.setFitWidth(400);
            imageView.setFitHeight(300);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/list_view_item.fxml"));
                Parent parent = loader.load();
                ListViewItemController controller = loader.getController();
                controller.setImage(imageView.getImage());
                controller.setName(e.getExercise().getName());
                controller.setDescription(e.getExercise().getDescription());
                controller.setPrice(String.format("%.2f руб.", e.getExercise().getPrice()));
                parent.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        showPopup(parent, e.getExercise().getId(), mouseEvent.getX(), mouseEvent.getY());
                    }
                });
                my_services_lv.getItems().add(parent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void showPopup(Parent parent, int exerciseId, double sceneX, double sceneY) {
        JFXButton button = new JFXButton("Удалить");
        button.setPrefSize(100, 50);
        JFXPopup popup = new JFXPopup(button);
        popup.show(parent, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT, sceneX, sceneY - 300);
        button.setOnMouseClicked(e -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("exercise_id", exerciseId);
            jsonObject.put("client_id", Session.getInstance().getClient().getId());
            serverSender.send(jsonObject, DaoAction.DELETE_CLIENT_SERVICE_BY_EXERCISE_ID);
            loadClientServices();
            popup.hide();
        });

    }

    private void loadProfile() {
        Client client = Session.getInstance().getClient();
        profile_name.setText(client.getName() == null ? "Еще не задано" : client.getName());
        profile_surname.setText(client.getSurname() == null ? "Еще не задано" : client.getSurname());
        profile_city.setText(client.getCity() == null ? "Еще не задано" : client.getCity());
        String phone;
        profile_phone.setText((phone = String.valueOf(client.getPhone())).equals("0") ? "Еще не задано" : phone);
        String height;
        profile_height.setText((height = String.valueOf(client.getHeight())).equals("0.0") ? "Еще не задано" : height);
        String weight;
        profile_weight.setText((weight = String.valueOf(client.getWeight())).equals("0.0") ? "Еще не задано" : weight);
        profile_login.setText(client.getAccount().getLogin());
    }

    private List<Exercise> loadExercises() {
        serverSender.send(new JSONObject(), DaoAction.GET_ALL_EXERCISES);
        JSONObject jsonExercises = serverSender.retrieve();
        Type exerciseList = new TypeToken<ArrayList<Exercise>>() {
        }.getType();
        return gson.fromJson(jsonExercises.getString("exercises"), exerciseList);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
