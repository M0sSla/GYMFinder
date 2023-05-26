package com.example.gymfinder.ui.currentTraining;

public class ExerciseItemTraining {
    private Integer id;
    private Integer repeats;
    private Integer weight;
    private String name;
    private String group;

    public ExerciseItemTraining(Integer id, Integer repeats, Integer weight, String name, String group) {
        this.id = id;
        this.repeats = repeats;
        this.weight = weight;
        this.name = name;
        this.group = group;
    }

    public ExerciseItemTraining(Integer id, String name, String group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public ExerciseItemTraining() {}

    public void setRepeats(Integer repeats) {
        this.repeats = repeats;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }
    public Integer getRepeats() {
        return repeats;
    }
    public Integer getWeight() {
        return weight;
    }
    public String getName() {
        return name;
    }
    public String getGroup() {
        return group;
    }
}
