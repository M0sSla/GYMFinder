package com.example.gymfinder.ui.training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.gymfinder.R;
import com.example.gymfinder.databinding.FragmentTrainingBinding;

import java.lang.annotation.Native;

public class TrainingFragment extends Fragment {

    private FragmentTrainingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TrainingViewModel trainingViewModel =
                new ViewModelProvider(this).get(TrainingViewModel.class);

        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTrainingFastStart;
        final Button button = binding.buttonFastStart;

        trainingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        trainingViewModel.getTextButton().observe(getViewLifecycleOwner(), button::setText);

        button.setOnClickListener(v -> {
            moveToCurrentTrainingFragment(transitionInfo());
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Bundle transitionInfo() {
        Bundle bundle = new Bundle();
        return bundle;
    }
    private void moveToCurrentTrainingFragment(Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_training_to_currentTrainingFragment, bundle);
    }
}