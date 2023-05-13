package com.example.gymfinder.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gymfinder.TrainingItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface TrainingDAO {

    @Insert
    Completable addTraining(TrainingItem trainingItem);

    /*@Query("SELECT * FROM training_table WHERE id = :id")
    Observable<TrainingItem> getTask(int id);*/


    @Query("SELECT * FROM training_table")
    Observable<List<TrainingItem>> getAllTraining();

}
