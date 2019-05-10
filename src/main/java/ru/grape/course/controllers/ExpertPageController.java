package ru.grape.course.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import ru.grape.course.controllers.commons.DaoAction;
import ru.grape.course.controllers.utils.DialogPopup;
import ru.grape.course.controllers.utils.ServerSender;
import ru.grape.course.model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class ExpertPageController {
    private ServerSender serverSender = ServerSender.getInstance();
    private Gson gson = new Gson();

    @FXML
    private Spinner<Integer> z11;

    @FXML
    private Spinner<Integer> z12;

    @FXML
    private Spinner<Integer> z13;

    @FXML
    private Spinner<Integer> z14;

    @FXML
    private Spinner<Integer> z15;

    @FXML
    private Spinner<Integer> z21;

    @FXML
    private Spinner<Integer> z22;

    @FXML
    private Spinner<Integer> z23;

    @FXML
    private Spinner<Integer> z24;

    @FXML
    private Spinner<Integer> z25;

    @FXML
    private Spinner<Integer> z31;

    @FXML
    private Spinner<Integer> z32;

    @FXML
    private Spinner<Integer> z33;

    @FXML
    private Spinner<Integer> z34;

    @FXML
    private Spinner<Integer> z35;

    @FXML
    private Spinner<Integer> z43;

    @FXML
    private Spinner<Integer> z41;

    @FXML
    private Spinner<Integer> z42;

    @FXML
    private Spinner<Integer> z44;

    @FXML
    private Spinner<Integer> z45;

    @FXML
    private Spinner<Integer> z51;

    @FXML
    private Spinner<Integer> z52;

    @FXML
    private Spinner<Integer> z53;

    @FXML
    private Spinner<Integer> z54;

    @FXML
    private Spinner<Integer> z55;

    @FXML
    private JFXTextField mz11;

    @FXML
    private JFXTextField mz12;

    @FXML
    private JFXTextField mz13;

    @FXML
    private JFXTextField mz14;

    @FXML
    private JFXTextField mz15;

    @FXML
    private JFXTextField mz21;

    @FXML
    private JFXTextField mz22;

    @FXML
    private JFXTextField mz23;

    @FXML
    private JFXTextField mz24;

    @FXML
    private JFXTextField mz25;

    @FXML
    private JFXTextField mz31;

    @FXML
    private JFXTextField mz32;

    @FXML
    private JFXTextField mz33;

    @FXML
    private JFXTextField mz34;

    @FXML
    private JFXTextField mz35;

    @FXML
    private JFXTextField mz41;

    @FXML
    private JFXTextField mz42;

    @FXML
    private JFXTextField mz43;

    @FXML
    private JFXTextField mz44;

    @FXML
    private JFXTextField mz45;

    @FXML
    private JFXTextField mz51;

    @FXML
    private JFXTextField mz52;

    @FXML
    private JFXTextField mz53;

    @FXML
    private JFXTextField mz54;

    @FXML
    private JFXTextField mz55;

    @FXML
    private JFXTextField w1;

    @FXML
    private JFXTextField w2;

    @FXML
    private JFXTextField w3;

    @FXML
    private JFXTextField w4;

    @FXML
    private JFXTextField w5;

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
    private PieChart pieChart;

    @FXML
    private JFXTextField overall_services_sum;

    @FXML
    private JFXTextField overall_services;

    @FXML
    private JFXTextField overall_clients;

    @FXML
    private JFXTextField overall_satisfaction;

    @FXML
    private JFXTextField overall_variety;

    @FXML
    private JFXTextField overall_time;

    @FXML
    private JFXTextField overall_trener_time;

    @FXML
    private JFXTextField overall_quality;

    private Stage stage;
    private List<Goal> goals;


    @FXML
    void calculate() {
        int n = 5;
        Integer z11Value = z11.getValue();
        Integer z12Value = z12.getValue();
        Integer z13Value = z13.getValue();
        Integer z14Value = z14.getValue();
        Integer z15Value = z15.getValue();

        Integer z21Value = z21.getValue();
        Integer z22Value = z22.getValue();
        Integer z23Value = z23.getValue();
        Integer z24Value = z24.getValue();
        Integer z25Value = z25.getValue();

        Integer z31Value = z31.getValue();
        Integer z32Value = z32.getValue();
        Integer z33Value = z33.getValue();
        Integer z34Value = z34.getValue();
        Integer z35Value = z35.getValue();

        Integer z41Value = z41.getValue();
        Integer z42Value = z42.getValue();
        Integer z43Value = z43.getValue();
        Integer z44Value = z44.getValue();
        Integer z45Value = z45.getValue();

        Integer z51Value = z51.getValue();
        Integer z52Value = z52.getValue();
        Integer z53Value = z53.getValue();
        Integer z54Value = z54.getValue();
        Integer z55Value = z55.getValue();

        mz11.setText(String.valueOf(n - z11Value));
        mz12.setText(String.valueOf(n - z12Value));
        mz13.setText(String.valueOf(n - z13Value));
        mz14.setText(String.valueOf(n - z14Value));
        mz15.setText(String.valueOf(n - z15Value));

        mz21.setText(String.valueOf(n - z21Value));
        mz22.setText(String.valueOf(n - z22Value));
        mz23.setText(String.valueOf(n - z23Value));
        mz24.setText(String.valueOf(n - z24Value));
        mz25.setText(String.valueOf(n - z25Value));

        mz31.setText(String.valueOf(n - z31Value));
        mz32.setText(String.valueOf(n - z32Value));
        mz33.setText(String.valueOf(n - z33Value));
        mz34.setText(String.valueOf(n - z34Value));
        mz35.setText(String.valueOf(n - z35Value));

        mz41.setText(String.valueOf(n - z41Value));
        mz42.setText(String.valueOf(n - z42Value));
        mz43.setText(String.valueOf(n - z43Value));
        mz44.setText(String.valueOf(n - z44Value));
        mz45.setText(String.valueOf(n - z45Value));

        mz51.setText(String.valueOf(n - z51Value));
        mz52.setText(String.valueOf(n - z52Value));
        mz53.setText(String.valueOf(n - z53Value));
        mz54.setText(String.valueOf(n - z54Value));
        mz55.setText(String.valueOf(n - z55Value));

        int K1 = (n - z11Value) + (n - z21Value) + (n - z31Value) + (n - z41Value);
        int K2 = (n - z12Value) + (n - z22Value) + (n - z32Value) + (n - z42Value);
        int K3 = (n - z13Value) + (n - z23Value) + (n - z33Value) + (n - z43Value);
        int K4 = (n - z14Value) + (n - z24Value) + (n - z34Value) + (n - z44Value);
        int K5 = (n - z15Value) + (n - z25Value) + (n - z35Value) + (n - z45Value);

        double W1 = (double) K1 / (K1 + K2 + K3 + K4 + K5);
        double W2 = (double) K2 / (K1 + K2 + K3 + K4 + K5);
        double W3 = (double) K3 / (K1 + K2 + K3 + K4 + K5);
        double W4 = (double) K4 / (K1 + K2 + K3 + K4 + K5);
        double W5 = (double) K5 / (K1 + K2 + K3 + K4 + K5);

        w1.setText(String.format("%.3f", W1));
        w2.setText(String.format("%.3f", W2));
        w3.setText(String.format("%.3f", W3));
        w4.setText(String.format("%.3f", W4));
        w5.setText(String.format("%.3f", W5));

        List<GoalPriority> goalPriorities = Stream.of(
                new GoalPriority(1, W1),
                new GoalPriority(2, W2),
                new GoalPriority(3, W3),
                new GoalPriority(4, W4),
                new GoalPriority(5, W5)
        ).sorted(Comparator.comparingDouble(o -> o.priority))
                .collect(Collectors.toList());
        Collections.reverse(goalPriorities);
        goalPriorities.get(0).integerPriority = 1;
        goalPriorities.get(1).integerPriority = 2;
        goalPriorities.get(2).integerPriority = 3;
        goalPriorities.get(3).integerPriority = 4;
        goalPriorities.get(4).integerPriority = 5;
        updateGoalsPriorities(goalPriorities);
    }

    private void updateGoalsPriorities(List<GoalPriority> goalPriorities) {
        List<Goal> goalsToUpdate = new ArrayList<>();
        goalsToUpdate.add(new Goal(1, goals.get(0).getName(), goalPriorities.stream()
                .filter(e -> e.id == 1)
                .findAny()
                .get()
                .getIntegerPriority()));
        goalsToUpdate.add(new Goal(2, goals.get(1).getName(), goalPriorities.stream()
                .filter(e -> e.id == 2)
                .findAny()
                .get()
                .getIntegerPriority()));
        goalsToUpdate.add(new Goal(3, goals.get(2).getName(), goalPriorities.stream()
                .filter(e -> e.id == 3)
                .findAny()
                .get()
                .getIntegerPriority()));
        goalsToUpdate.add(new Goal(4, goals.get(3).getName(), goalPriorities.stream()
                .filter(e -> e.id == 4)
                .findAny()
                .get()
                .getIntegerPriority()));
        goalsToUpdate.add(new Goal(5, goals.get(4).getName(), goalPriorities.stream()
                .filter(e -> e.id == 5)
                .findAny()
                .get()
                .getIntegerPriority()));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goals", gson.toJson(goalsToUpdate));
        serverSender.send(jsonObject, DaoAction.UPDATE_GOALS);
        DialogPopup.showDialog(stage, "Успех!", "Приоритеты и цели упешно обновлены!", 450, 200);
        loadGoals();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class GoalPriority {
        private int id;
        private double priority;
        private int integerPriority;

        public GoalPriority(int id, double priority) {
            this.id = id;
            this.priority = priority;
        }
    }

    @FXML
    void cancelAll() {
        Stream.of(z11, z12, z13, z14, z15,
                z21, z22, z23, z24, z25,
                z31, z32, z33, z34, z35,
                z41, z42, z43, z44, z45,
                z51, z52, z53, z54, z55)
                .forEach(e -> e.getEditor().setText("1"));
        Stream.of(mz11, mz12, mz13, mz14, mz15,
                mz21, mz22, mz23, mz24, mz25,
                mz31, mz32, mz33, mz34, mz35,
                mz41, mz42, mz43, mz44, mz45,
                mz51, mz52, mz53, mz54, mz55,
                w1, w2, w3, w4, w5).forEach(e -> e.setText(""));
    }

    @FXML
    void help() {
        DialogPopup.showDialog(stage, "Пояснение", "Была созвана группа экспертов из 5-х человек для определения приоритетов целей.\n" +
                "Каждый эксперт оценивает приоритетность цели от 1 до 10. \n Когда эксперты окончат расставлять свои оценки, нажать кнопку 'рассчитать'.", 450, 200);

    }

    public void initialize() {
        Stream.of(z11, z12, z13, z14, z15,
                z21, z22, z23, z24, z25,
                z31, z32, z33, z34, z35,
                z41, z42, z43, z44, z45,
                z51, z52, z53, z54, z55)
                .forEach(e ->
                        e.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                1,
                                5,
                                1,
                                1)));
        loadOverallStatistics();
        loadGoals();
    }

    private void loadOverallStatistics() {
        serverSender.send(new JSONObject(), DaoAction.GET_ALL_CLIENTS);
        JSONObject jsonClients = serverSender.retrieve();
        Type clientsList = new TypeToken<ArrayList<Client>>() {}.getType();
        List<Client> clients = gson.fromJson(jsonClients.getString("clients"), clientsList);
        overall_clients.setText(String.valueOf(clients.size()));

        serverSender.send(new JSONObject(), DaoAction.GET_ALL_SERVICES);
        JSONObject jsonServices = serverSender.retrieve();
        Type servicesList = new TypeToken<ArrayList<Service>>() {}.getType();
        List<Service> services = gson.fromJson(jsonServices.getString("services"), servicesList);
        overall_services.setText(String.valueOf(services.size()));

        overall_services_sum.setText(String.format("%.2f", services.stream()
                .map(Service::getExercise)
                .map(Exercise::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum()));

        serverSender.send(new JSONObject(), DaoAction.GET_RATES);
        JSONObject retrieve = serverSender.retrieve();
        Type ratesList = new TypeToken<ArrayList<Rate>>() {}.getType();
        List<Rate> rates = gson.fromJson(retrieve.getString("rates"), ratesList);
        rates.stream()
                .map(Rate::getVariety)
                .mapToDouble(Integer::doubleValue)
                .average()
                .ifPresent(value -> overall_variety.setText(String.valueOf(value)));
        rates.stream()
                .map(Rate::getSatisfaction)
                .mapToDouble(Integer::doubleValue)
                .average()
                .ifPresent(value -> overall_satisfaction.setText(String.valueOf(value)));
        rates.stream()
                .map(Rate::getExercise_time)
                .mapToDouble(Integer::doubleValue)
                .average()
                .ifPresent(value -> overall_time.setText(String.valueOf(value)));
        rates.stream()
                .map(Rate::getTrainers_time)
                .mapToDouble(Integer::doubleValue)
                .average()
                .ifPresent(value -> overall_trener_time.setText(String.valueOf(value)));
        rates.stream()
                .map(Rate::getQuality)
                .mapToDouble(Integer::doubleValue)
                .average()
                .ifPresent(value -> overall_quality.setText(String.valueOf(value)));
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Разнообразие", Double.valueOf(overall_variety.getText())),
                new PieChart.Data("Удовлетворенность", Double.valueOf(overall_satisfaction.getText())),
                new PieChart.Data("Время для посещения", Double.valueOf(overall_time.getText())),
                new PieChart.Data("Время от тренера", Double.valueOf(overall_trener_time.getText())),
                new PieChart.Data("Цена/качество", Double.valueOf(overall_quality.getText())));
        pieChart.setData(pieChartData);
    }

    private void loadGoals() {
        serverSender.send(new JSONObject(), DaoAction.GET_GOALS);
        JSONObject inputObject = serverSender.retrieve();
        Type goalsList = new TypeToken<ArrayList<Goal>>() {}.getType();
        List<Goal> goals = gson.fromJson(inputObject.getString("goals"), goalsList);
        loadGoal(goal_1_spinner, aim_1_tf, goals.get(0));
        loadGoal(goal_2_spinner, aim_2_tf, goals.get(1));
        loadGoal(goal_3_spinner, aim_3_tf, goals.get(2));
        loadGoal(goal_4_spinner, aim_4_tf, goals.get(3));
        loadGoal(goal_5_spinner, aim_5_tf, goals.get(4));
        this.goals = goals;
    }

    private void loadGoal(Spinner<Integer> goal_spinner, JFXTextField goal_text, Goal goal) {
        goal_spinner.getEditor().setText(String.valueOf(goal.getPriority()));
        goal_text.setText(goal.getName());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
