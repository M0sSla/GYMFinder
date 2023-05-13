package com.example.gymfinder.ui.addExercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfinder.databinding.FragmentExercisesBinding;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {

    FragmentExercisesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayList<ExerciseItem> data = new ArrayList<>();
        data.add(new ExerciseItem("Отжимания", "Грудные мышцы"));
        data.add(new ExerciseItem("Подтягивания", "Широчайшие мышцы"));
        binding.recExercises
                .setLayoutManager(new LinearLayoutManager(getContext()));
        ExerciseAdapter adapter =
                new ExerciseAdapter(data);
        binding.recExercises.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
