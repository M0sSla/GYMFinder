package com.example.gymfinder.ui.finder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gymfinder.R;
import com.example.gymfinder.databinding.FragmentFinderBinding;

public class FinderFragment extends Fragment {
    private FragmentFinderBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFinderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        binding.message2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        return root;
    }

    private void createDialog() {
        Dialog dialog = Dialog.newInstance("https://www.google.com/ - dfhfisud");
        assert getFragmentManager() != null;
        dialog.show(getFragmentManager(), "my_dialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
