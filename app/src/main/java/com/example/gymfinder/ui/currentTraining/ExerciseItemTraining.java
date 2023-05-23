package com.example.gymfinder.ui.currentTraining;

public class ExerciseItemTraining {
    private Integer id;
    private Integer repeats;
    private String name;
    private String group;

    public ExerciseItemTraining(Integer id, Integer repeats, String name, String group) {
        this.id = id;
        this.repeats = repeats;
        this.name = name;
        this.group = group;
    }

    public ExerciseItemTraining() {}

    public Integer getId() {
        return id;
    }
    public Integer getRepeats() {
        return repeats;
    }

    public String getName() {
        return name;
    }
    public String getGroup() {
        return group;
    }
}
