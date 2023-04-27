package com.example.gymfinder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfinder.databinding.TrainingItemBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    private ArrayList<TrainingItem> data;

    public TrainingAdapter(ArrayList<TrainingItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TrainingItemBinding binding =TrainingItemBinding
                .inflate(LayoutInflater.from(
                        parent.getContext()),
                        parent,
                        false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingAdapter.ViewHolder holder, int position) {
        holder.binding.volume.setText((data.get(position).volume) + " Кг");
        holder.binding.duration.setText((data.get(position).time) + " Мин");
        holder.binding.date.setText("Когда-то очень давно!");
        holder.binding.info.setText(data.get(position).info);
        holder.binding.nickname.setText(data.get(position).nickname);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TrainingItemBinding binding;
        public ViewHolder(TrainingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}