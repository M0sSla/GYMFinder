package com.example.gymfinder.ui.addExercise;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfinder.R;
import com.example.gymfinder.TrainingAdapter;
import com.example.gymfinder.databinding.ExerciseItemBinding;
import com.example.gymfinder.databinding.TrainingItemBinding;
import com.example.gymfinder.ui.currentTraining.ExerciseItemTraining;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

    private final ArrayList<ExerciseItem> data;

    public ExerciseAdapter(ArrayList<ExerciseItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseItemBinding binding = ExerciseItemBinding
                .inflate(LayoutInflater.from(
                                parent.getContext()),
                        parent,
                        false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.binding.IMAGE.setsrc че-то там, бла-бла-бла
        holder.binding.nameExercise.setText(data.get(position).getName());
        holder.binding.groupExercise.setText(data.get(position).getGroup());
        holder.binding.exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciseItemTraining newItem =
                        new ExerciseItemTraining(
                                data.get(position).getId(), null, null, data.get(position).getName(), data.get(position).getGroup()
                        );

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference currentTraining = database.getReference("user").child((FirebaseAuth.getInstance()).getCurrentUser().getUid()).child("currentTraining");

                String exerciseTrainingId = currentTraining.push().getKey();
                currentTraining.child(exerciseTrainingId).setValue(newItem);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("id", data.get(position));
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_exercisesFragment_to_currentTrainingFragment/*, bundle*/);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ExerciseItemBinding binding;
        public ExerciseViewHolder(ExerciseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
