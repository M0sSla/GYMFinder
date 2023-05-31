package com.example.gymfinder.ui.currentTraining;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfinder.R;
import com.example.gymfinder.TrainingItem;
import com.example.gymfinder.databinding.FragmentCurrentTrainingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentTrainingFragment extends Fragment {

    //
    FragmentCurrentTrainingBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentUser = (FirebaseAuth.getInstance()).getCurrentUser();


    DatabaseReference user = database.getReference("user").child(currentUser.getUid());
    DatabaseReference currentTraining = database.getReference("user").child(currentUser.getUid()).child("currentTraining");
    DatabaseReference training = database.getReference("user").child(currentUser.getUid()).child("training");
    DatabaseReference currentVolume = database.getReference("user").child(currentUser.getUid()).child("currentVolume");
    DatabaseReference currentInfo = database.getReference("user").child(currentUser.getUid()).child("currentInfo");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArrayList<ExerciseItemTraining> exerciseData = new ArrayList<>();

        binding = FragmentCurrentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.newExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_currentTrainingFragment_to_exercisesFragment);
            }
        });

        /*user.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Integer volume = 0;
                    user.child("tmp").setValue(snapshot.toString());
                    Toast.makeText(getContext(), snapshot.toString(), Toast.LENGTH_LONG).show();
                    if (snapshot.child("currentTraining").exists()) {
                        for (DataSnapshot snap : snapshot.child("currentTraining").getChildren()) {
                            ExerciseItemTraining tmp = snap.getValue(ExerciseItemTraining.class);
                            volume += tmp.getRepeats() * tmp.getWeight();
                        }
                    }
                    user.child("currentVolume").setValue(volume);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Integer volume = 0;
                    if (snapshot.child("currentTraining").exists()) {
                        for (DataSnapshot snap : snapshot.child("currentTraining").getChildren()) {
                            ExerciseItemTraining tmp = snap.getValue(ExerciseItemTraining.class);
                            volume += tmp.getRepeats() * tmp.getWeight();
                        }
                    }
                    user.child("currentVolume").setValue(volume);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer volume = 0;
                    if (snapshot.child("currentTraining").exists()) {
                        for (DataSnapshot snap : snapshot.child("currentTraining").getChildren()) {
                            ExerciseItemTraining tmp = snap.getValue(ExerciseItemTraining.class);
                            if (tmp.getRepeats() != null && tmp.getWeight() != null) {
                                volume += tmp.getRepeats() * tmp.getWeight();
                            }
                        }
                    }
                    user.child("currentVolume").setValue(volume);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        currentVolume.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && binding != null) {
                    if (!snapshot.getValue().toString().equals(binding.volume.getText())) {
                        binding.volume.setText(snapshot.getValue().toString());
                    }
                    else {
                        binding.volume.setText("0");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        binding.editInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    currentInfo.setValue(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        currentInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.getValue() != null) {
                        // обрезка лишних пробелов
                        String tmp = snapshot.getValue().toString().trim();
                        binding.editInfo.setText(tmp);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // окончание тренировки, создание нового объекта в список trainings
        binding.endTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference("user")
                        .child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String nickname = snapshot.child("nickname").getValue().toString();
                                    Integer volume = Integer.parseInt(snapshot.child("currentVolume").getValue().toString());
                                    String info;
                                    if (snapshot.child("currentInfo").exists()) {
                                        info = snapshot.child("currentInfo").getValue().toString();
                                    }
                                    else {
                                        info = "";
                                    }
                                    TrainingItem newTraining =
                                            new TrainingItem(0, volume, nickname, info, 0);
                                    String trainingId = training.push().getKey();
                                    training.child(trainingId).setValue(newTraining);
                                    currentTraining.removeValue();
                                    currentVolume.setValue(0);
                                    currentInfo.setValue("");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                // Добавление класса trainingItem в базу данных

                // Здесь должна быть обработка текущей тренировки, добавления её в список trainings и удаление элемента currentTraining
                Navigation.findNavController(root).navigate(R.id.action_currentTrainingFragment_to_navigation_training2);
            }
        });

        // Работа с базой данных.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        binding.recExercises
                .setLayoutManager(new LinearLayoutManager(getContext()));
        ExerciseTrainingAdapter adapter =
                new ExerciseTrainingAdapter(exerciseData);
        binding.recExercises.setAdapter(adapter);

        currentTraining.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ExerciseItemTraining tmp = snapshot.getValue(ExerciseItemTraining.class);
                exerciseData.add(tmp);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ExerciseItemTraining tmp = snapshot.getValue(ExerciseItemTraining.class);
                if (exerciseData.contains(tmp)) {
                    exerciseData.remove(tmp);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        currentVolume.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.volume.setText(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
