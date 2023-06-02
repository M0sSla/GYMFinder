package com.example.gymfinder.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gymfinder.TrainingItem;

/**
 * Загатовка под локальную бд
 */
@Database(entities = {TrainingItem.class}, version = 1)
public abstract class TrainingDB extends RoomDatabase {
    private static TrainingDB instance;

    public abstract TrainingDAO TrainingDAO();

    public static synchronized TrainingDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TrainingDB.class, "training_table").build();
            // создать экземпляр класса и поместить его в переменную instance
        }
        return instance;
    }
}
