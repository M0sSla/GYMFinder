package com.example.gymfinder.ui.currentTraining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.gymfinder.databinding.FragmentCurrentTrainingBinding;

public class CurrentTrainingFragment extends Fragment {

    FragmentCurrentTrainingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCurrentTrainingBinding.inflate(inflater, container, false);
        CurrentTrainingViewModel currentTrainingViewModel = new ViewModelProvider(this).get(CurrentTrainingViewModel.class);
        binding.endTraining.setOnClickListener(v -> {
            currentTrainingViewModel.endTraining(v, binding);
            currentTrainingViewModel.returnBack(v);
        });


        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
