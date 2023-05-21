package com.example.gymfinder.ui.currentTraining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfinder.R;
import com.example.gymfinder.databinding.FragmentCurrentTrainingBinding;
import com.example.gymfinder.ui.addExercise.ExerciseAdapter;
import com.example.gymfinder.ui.addExercise.ExerciseItem;

import java.util.ArrayList;

public class CurrentTrainingFragment extends Fragment {

    FragmentCurrentTrainingBinding binding;
    protected ArrayList<ExerciseItem> exerciseData = new ArrayList<>();
    protected Integer cnt = 0;
    private ExerciseAdapter adapter =
            new ExerciseAdapter(exerciseData);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCurrentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recExercises
                .setLayoutManager(new LinearLayoutManager(getContext()));

        binding.recExercises.setAdapter(adapter);

        binding.newExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_currentTrainingFragment_to_exercisesFragment);
            }
        });

        binding.endTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_currentTrainingFragment_to_navigation_training2);
            }
        });

        return root;
    }



    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        ExerciseItem newItem = (ExerciseItem) getArguments().getSerializable("id");
        if (!exerciseData.contains(newItem) && newItem != null) {
            cnt++;
            exerciseData.add(newItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
