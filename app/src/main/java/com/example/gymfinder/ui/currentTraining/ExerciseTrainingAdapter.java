package com.example.gymfinder.ui.currentTraining;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfinder.databinding.ExerciseTrainingItemBinding;

import java.util.ArrayList;

public class ExerciseTrainingAdapter extends RecyclerView.Adapter<ExerciseTrainingAdapter.ExerciseTrainingViewHolder> {

    private final ArrayList<ExerciseItemTraining> data;

    public ExerciseTrainingAdapter(ArrayList<ExerciseItemTraining> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ExerciseTrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseTrainingItemBinding binding = ExerciseTrainingItemBinding
                .inflate(LayoutInflater.from(
                        parent.getContext()), parent, false
                );
        return new ExerciseTrainingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseTrainingViewHolder holder, int position) {
        holder.binding.nameExerciseTraining.setText(data.get(position).getName());
        holder.binding.groupExerciseTraining.setText(data.get(position).getGroup());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ExerciseTrainingViewHolder extends RecyclerView.ViewHolder {
        ExerciseTrainingItemBinding binding;
        public ExerciseTrainingViewHolder(ExerciseTrainingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
