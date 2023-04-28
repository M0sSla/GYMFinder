package com.example.gymfinder;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "training_table")
public class TrainingItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private int time;
    private int volume;
    private String nickname;
    private String info;
    private int date;
    // необходимо разрешить проблему с хранением времени

    public TrainingItem(int time, int volume, String nickname, String info, int date) {
        this.time = time;
        this.volume = volume;
        this.nickname = nickname;
        this.info = info;
        this.date = 10;
    }

    public int getTime() {
        return time;
    }
    public int getVolume() {
        return volume;
    }
    public String getNickname() {
        return nickname;
    }
    public String getInfo() {
        return info;
    }
    public int getDate() {
        return date;
    }

}