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

    /**
     *  Перегруз метода для основная логики работы фрагмента
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     *
     * @return root
     */
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


    /**
     * Создание маленького диалогового окна, которое отсылает к bios пользователя
     */
    private void createDialog() {
        Dialog dialog = Dialog.newInstance("https://www.google.com/ - dfhfisud");
        assert getFragmentManager() != null;
        dialog.show(getFragmentManager(), "my_dialog");
    }

    /**
     * Перегруз метода для обнуления binding
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
