package com.example.gymfinder.ui.addExercise;

import java.io.Serializable;

public class ExerciseItem implements Serializable {

    private Integer id;
    private String name;
    private String group;

    public ExerciseItem(Integer id, String name, String group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getGroup() {
        return group;
    }
}
