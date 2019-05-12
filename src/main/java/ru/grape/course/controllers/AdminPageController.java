package ru.grape.course.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONObject;
import ru.grape.course.controllers.commons.DaoAction;
import ru.grape.course.controllers.utils.DialogPopup;
import ru.grape.course.controllers.utils.ServerSender;
import ru.grape.course.dao.Datasource;
import ru.grape.course.model.Account;
import ru.grape.course.model.Goal;
import ru.grape.course.model.Role;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminPageController {
    private final Gson gson = new Gson();
    private final ServerSender serverSender = ServerSender.getInstance();

    @FXML
    private TableView<Account> expert_tv;

    @FXML
    private JFXTextField expert_login_tf;

    @FXML
    private JFXTextField expert_password_tf;

    @FXML
    private Spinner<Integer> goal_1_spinner;

    @FXML
    private JFXTextField aim_1_tf;

    @FXML
    private Spinner<Integer> goal_2_spinner;

    @FXML
    private JFXTextField aim_2_tf;

    @FXML
    private Spinner<Integer> goal_3_spinner;

    @FXML
    private JFXTextField aim_3_tf;

    @FXML
    private Spinner<Integer> goal_4_spinner;

    @FXML
    private JFXTextField aim_4_tf;

    @FXML
    private Spinner<Integer> goal_5_spinner;

    @FXML
    private JFXTextField aim_5_tf;

    @FXML
    private TableView<Account> client_tv;

    @FXML
    private JFXTextField client_login_tf;

    @FXML
    private JFXTextField client_password_tf;
    private Stage stage;

    @FXML
    public void client_add() {
        String login, password;
        if (!(login = client_login_tf.getText()).isEmpty() && !(password = client_password_tf.getText()).isEmpty()) {
            Account account = Account.builder()
                    .login(login)
                    .password(password)
                    .role(Role.USER)
                    .build();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", gson.toJson(account));
            serverSender.send(jsonObject, DaoAction.ADD_CLIENT_HAVING_LOGIN_PASSWORD_ROLE);
            hashPassword(account);
            client_tv.getItems().add(account);
            DialogPopup.showDialog(stage, "Успех!", "Клиент успешно добавлен!", 400, 200);
        }
    }

    private void hashPassword(Account account) {
        JSONObject object = new JSONObject();
        object.put("toHash", account.getPassword());
        serverSender.send(object, DaoAction.GIVE_ME_HASH);
        JSONObject retrieve = serverSender.retrieve();
        account.setPassword(retrieve.getString("hashed"));
    }

    @FXML
    public void client_delete() {
        if (client_tv.getSelectionModel().getSelectedItem() != null) {
            String login = client_tv.getSelectionModel().getSelectedItem().getLogin();
            client_tv.getItems().remove(client_tv.getSelectionModel().getSelectedItem());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", login);
            serverSender.send(jsonObject, DaoAction.DELETE_CLIENT_BY_LOGIN);
            DialogPopup.showDialog(stage, "Успех!", "Клиент [" + login + "] успешно удален", 450, 200);

        }
    }

    @FXML
    public void expert_add() {
        String login, password;
        if (!(login = expert_login_tf.getText()).isEmpty() && !(password = expert_password_tf.getText()).isEmpty()) {
            Account account = Account.builder()
                    .login(login)
                    .password(password)
                    .role(Role.EXPERT)
                    .build();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", gson.toJson(account));
            serverSender.send(jsonObject, DaoAction.ADD_CLIENT_HAVING_LOGIN_PASSWORD_ROLE);
            hashPassword(account);
            expert_tv.getItems().add(account);
            DialogPopup.showDialog(stage, "Успех!", "Эксперт успешно добавлен!", 400, 200);
        }
    }

    @FXML
    public void expert_delete() {
        if (expert_tv.getSelectionModel().getSelectedItem() != null) {
            String login = expert_tv.getSelectionModel().getSelectedItem().getLogin();
            expert_tv.getItems().remove(expert_tv.getSelectionModel().getSelectedItem());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", login);
            serverSender.send(jsonObject, DaoAction.DELETE_CLIENT_BY_LOGIN);
            DialogPopup.showDialog(stage, "Успех!", "Эксперт [" + login + "] успешно удален", 400, 200);
        }
    }

    @FXML
    public void aim_update() {
        List<Goal> goalsToUpdate = new ArrayList<>();
        goalsToUpdate.add(new Goal(goal_1_spinner, aim_1_tf));
        goalsToUpdate.add(new Goal(goal_2_spinner, aim_2_tf));
        goalsToUpdate.add(new Goal(goal_3_spinner, aim_3_tf));
        goalsToUpdate.add(new Goal(goal_4_spinner, aim_4_tf));
        goalsToUpdate.add(new Goal(goal_5_spinner, aim_5_tf));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goals", gson.toJson(goalsToUpdate));
        serverSender.send(jsonObject, DaoAction.UPDATE_GOALS);
        DialogPopup.showDialog(stage, "Успех!", "Приоритеты и цели упешно обновлены!", 450, 200);
    }

    public void initialize() {
        setValueFactory(goal_1_spinner, 1);
        setValueFactory(goal_2_spinner, 2);
        setValueFactory(goal_3_spinner, 3);
        setValueFactory(goal_4_spinner, 4);
        setValueFactory(goal_5_spinner, 5);
        client_tv.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("login"));
        client_tv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("password"));
        expert_tv.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("login"));
        expert_tv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("password"));
        loadExperts();
        loadClients();
        loadGoals();
    }

    private void loadGoals() {
        serverSender.send(new JSONObject(), DaoAction.GET_GOALS);
        JSONObject inputObject = serverSender.retrieve();
        Type goalsList = new TypeToken<ArrayList<Goal>>() {
        }.getType();
        List<Goal> goals = gson.fromJson(inputObject.getString("goals"), goalsList);
        loadGoal(goal_1_spinner, aim_1_tf, goals.get(0));
        loadGoal(goal_2_spinner, aim_2_tf, goals.get(1));
        loadGoal(goal_3_spinner, aim_3_tf, goals.get(2));
        loadGoal(goal_4_spinner, aim_4_tf, goals.get(3));
        loadGoal(goal_5_spinner, aim_5_tf, goals.get(4));
    }

    private void loadGoal(Spinner<Integer> goal_spinner, JFXTextField goal_text, Goal goal) {
        goal_spinner.getEditor().setText(String.valueOf(goal.getPriority()));
        goal_text.setText(goal.getName());
    }

    private void loadClients() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", gson.toJson(Role.USER));
        serverSender.send(jsonObject, DaoAction.GET_USERS);
        JSONObject json = serverSender.retrieve();
        Type usersList = new TypeToken<ArrayList<Account>>() {
        }.getType();
        List<Account> users = gson.fromJson(json.getString("users"), usersList);
        client_tv.getItems().addAll(users);

    }

    private void loadExperts() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", gson.toJson(Role.EXPERT));
        serverSender.send(jsonObject, DaoAction.GET_EXPERTS);
        JSONObject json = serverSender.retrieve();
        Type expertList = new TypeToken<ArrayList<Account>>() {
        }.getType();
        List<Account> experts = gson.fromJson(json.getString("experts"), expertList);
        expert_tv.getItems().addAll(experts);
    }

    private void setValueFactory(Spinner<Integer> spinner, int index) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, index);
        spinner.setValueFactory(valueFactory);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
