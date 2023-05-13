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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCurrentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.newExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_currentTrainingFragment_to_exercisesFragment);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        ExerciseItem newItem = (ExerciseItem) getArguments().getSerializable(Integer.toString(cnt));
        binding.recExercises
                .setLayoutManager(new LinearLayoutManager(getContext()));
        if (exerciseData.contains(newItem)) {
            cnt++;
            exerciseData.add(newItem);
        }
        ExerciseAdapter adapter =
                new ExerciseAdapter(exerciseData);
        binding.recExercises.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
