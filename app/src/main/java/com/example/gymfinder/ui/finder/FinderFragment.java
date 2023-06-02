package com.example.gymfinder.ui.finder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymfinder.R;
import com.example.gymfinder.databinding.FragmentFinderBinding;

public class FinderFragment extends Fragment {
    private FragmentFinderBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FinderViewModel finderViewModel =
                new ViewModelProvider(this).get(FinderViewModel.class);
        binding = FragmentFinderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textFinder;
        finderViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        @SuppressLint("ResourceType") Animation swipeLeftAnimation = AnimationUtils.loadAnimation(getContext(), R.drawable.swipe_left);
        @SuppressLint("ResourceType") Animation swipeRightAnimation = AnimationUtils.loadAnimation(getContext(), R.drawable.swipe_right);

        binding.dislike2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.Fragment.startAnimation(swipeLeftAnimation);
            }
        });

        binding.like2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.Fragment.startAnimation(swipeRightAnimation);
            }
        });

        binding.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.Fragment.startAnimation(swipeLeftAnimation);
            }
        });

        binding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.Fragment.startAnimation(swipeRightAnimation);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
