package com.example.gymfinder.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfinder.R;
import com.example.gymfinder.DataBase.TrainingDAO;
import com.example.gymfinder.DataBase.TrainingDB;
import com.example.gymfinder.TrainingAdapter;
import com.example.gymfinder.TrainingItem;
import com.example.gymfinder.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        TrainingDB trainingDB = TrainingDB.getInstance(requireContext());
        TrainingDAO trainingDAO = trainingDB.TrainingDAO();
        trainingDAO.getAllTraining().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onTaskLoaded);

        View root = binding.getRoot();

        final TextView textView = binding.nickname;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(root).navigate(R.id.action_navigation_profile_to_authorizationFragment);
            }
        });

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
    }
}