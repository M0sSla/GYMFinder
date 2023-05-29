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

    public TrainingItem() {
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


    public void setTime(int time) {
        this.time = time;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDate(int date) {
        this.date = date;
    }
}