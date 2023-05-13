package com.example.gymfinder.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gymfinder.R;
import com.example.gymfinder.databinding.ActivityMainBinding;
import com.example.gymfinder.databinding.FragmentRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    //private ActivityMainBinding activityMainBinding;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        auth = FirebaseAuth.getInstance();
        //activityMainBinding.navView.setVisibility(View.INVISIBLE);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
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

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Некорректная почта", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Пароль должен быть длиной не менее 8 символов", Toast.LENGTH_SHORT).show();
                    return;
                }
                // ВОТ ПОСЛЕ ЭТОГО ЛОМАЕТСЯ ПИЗДЕЦ!
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                binding.progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(root).navigate(R.id.action_registrationFragment_to_authorizationFragment);
                                } else {
                                    Toast.makeText(getContext(), "Ошибка регистрации",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activityMainBinding.navView.setVisibility(View.VISIBLE);
                Navigation.findNavController(root).navigate(R.id.action_registrationFragment_to_authorizationFragment);
            }
        });

        return root;
    }
}
