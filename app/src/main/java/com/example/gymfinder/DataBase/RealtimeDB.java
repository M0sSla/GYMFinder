package com.example.gymfinder.DataBase;

import com.example.gymfinder.TrainingItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public interface RealtimeDB {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://gymfinder-c418-default-rtdb.firebaseio.com/");
    DatabaseReference myRef =database.getReference("trainings");
    ArrayList<TrainingItem> messages = new ArrayList<>();


}
