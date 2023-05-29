package com.example.gymfinder.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymfinder.databinding.FragmentTrainingInfoBinding;

public class TrainingInfoFragment extends Fragment {

    FragmentTrainingInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentTrainingInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}
