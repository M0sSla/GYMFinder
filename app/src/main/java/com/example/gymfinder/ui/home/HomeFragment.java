package com.example.gymfinder.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfinder.DataBase.TrainingDAO;
import com.example.gymfinder.DataBase.TrainingDB;
import com.example.gymfinder.TrainingAdapter;
import com.example.gymfinder.TrainingItem;
import com.example.gymfinder.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Disposable trainingListDisposable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        TrainingDB trainingDB = TrainingDB.getInstance(requireContext());
        TrainingDAO trainingDAO = trainingDB.TrainingDAO();
        trainingListDisposable = trainingDAO
                .getAllTraining()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTaskLoaded);

        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void onTaskLoaded(List<TrainingItem> trainingItems) {
        TrainingAdapter adapter = new TrainingAdapter((ArrayList<TrainingItem>) trainingItems);
        binding.recyclerTraining.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.recyclerTraining.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        trainingListDisposable.dispose();
    }
}