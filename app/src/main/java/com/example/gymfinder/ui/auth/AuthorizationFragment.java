package com.example.gymfinder.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gymfinder.MainActivity;
import com.example.gymfinder.R;
import com.example.gymfinder.databinding.ActivityMainBinding;
import com.example.gymfinder.databinding.FragmentAuthorizationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationFragment extends BaseAuth {

    private FragmentAuthorizationBinding binding;
    private ActivityMainBinding activityMainBinding;

    FirebaseAuth auth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            //activityMainBinding.navView.setVisibility(View.VISIBLE);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authorizationFragment_to_navigation_home);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //activityMainBinding.navView.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        //bottomNavigationView.setVisibility(View.GONE);

        binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(binding.fieldEmail.getText());
                password = String.valueOf(binding.fieldPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Введите почту", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Некорректная почта", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Пароль должен быть длиной не менее 8 символов", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                binding.progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Успешный вход", Toast.LENGTH_SHORT).show();
                                    //activityMainBinding.navView.setVisibility(View.VISIBLE);
                                    Navigation.findNavController(root).navigate(R.id.action_authorizationFragment_to_navigation_home);
                                } else {
                                    Toast.makeText(getContext(), "Ошибка входа",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.emailCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_authorizationFragment_to_registrationFragment);
            }
        });

        return root;
    }
}
