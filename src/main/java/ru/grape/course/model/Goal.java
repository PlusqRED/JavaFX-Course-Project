package ru.grape.course.model;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Spinner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {
    private Integer id;
    private String name;
    private Integer priority;

    public Goal(Spinner<Integer> goal_1_spinner, JFXTextField aim_1_tf) {
        this.name = aim_1_tf.getText();
        this.priority= goal_1_spinner.getValue();
    }
}
