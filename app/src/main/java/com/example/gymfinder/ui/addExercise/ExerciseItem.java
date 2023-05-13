package com.example.gymfinder.ui.addExercise;

import java.io.Serializable;

public class ExerciseItem implements Serializable {

    private String name;
    private String group;

    public ExerciseItem(String name, String group) {
        this.name = name;
        this.group = group;
    }
    public String getName() {
        return name;
    }
    public String getGroup() {
        return group;
    }
}
