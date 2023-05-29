package com.example.gymfinder.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gymfinder.R;
import com.example.gymfinder.DataBase.TrainingDAO;
import com.example.gymfinder.DataBase.TrainingDB;
import com.example.gymfinder.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private boolean isConnected;
    private FragmentProfileBinding binding;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    FirebaseAuth auth;
    FirebaseUser user;
    // Получение ссылки на хранилище Firebase Storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userCurrent = database.getReference("user");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        TrainingDB trainingDB = TrainingDB.getInstance(requireContext());
        TrainingDAO trainingDAO = trainingDB.TrainingDAO();

        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Navigation.findNavController(root).navigate(R.id.action_navigation_profile_to_authorizationFragment);
        }
        else {
            binding.countOfFriends.setText("0");
            binding.countOfTrainings.setText("0");
            userCurrent.child(user.getUid()).child("email").setValue(user.getEmail());

            // аватар
            // listener получения значения аватарки
            ValueEventListener avatarListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String avatarUrl = dataSnapshot.getValue(String.class);

                        // Использование библиотеки Picasso для загрузки и отображения аватарки
                        Picasso.get().load(avatarUrl).into(binding.profileIcon);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Обработка ошибок получения значения аватарки
                    binding.profileIcon.setImageResource(R.drawable.avatar);
                }
            };
            // listener на узел аватарки текущего пользователя
            userCurrent.child(user.getUid()).child("avatarUrl").addListenerForSingleValueEvent(avatarListener);

            // никнейм
            // listener получения значения имени пользователя
            ValueEventListener usernameListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.getValue(String.class);
                        binding.nickname.setText(username);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Обработка ошибки получения значения
                    binding.nickname.setText("Имя пользователя.");
                }
            };

            // listener на узел информации текущего пользователя
            userCurrent.child(user.getUid()).child("nickname").addListenerForSingleValueEvent(usernameListener);
            // биос/инфо о пользователе
            // listener получения значения информации
            ValueEventListener biosListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String bios = dataSnapshot.getValue(String.class);
                        binding.bios.setText(bios);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Обработка ошибки получения значения
                    binding.bios.setText("Какая-то информация про человека");
                }
            };

            // listener на узел информации текущего пользователя
            userCurrent.child(user.getUid()).child("bios").addListenerForSingleValueEvent(biosListener);

            // listener на узел информации текущего пользователя

            // кол-во тренировок пользователей
            // listener получения значения тренировок
            ValueEventListener trainingsListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String trainings = dataSnapshot.getValue(String.class);
                        binding.bios.setText(trainings);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Обработка ошибки получения значения
                    binding.bios.setText("-1");
                }
            };

            // listener на узел информации текущего пользователя
            userCurrent.child(user.getUid()).child("trainings").addListenerForSingleValueEvent(trainingsListener);
            binding.changeNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.editNickname.setText("");

                    binding.nickname.setVisibility(View.GONE);
                    binding.changeNickname.setVisibility(View.GONE);

                    binding.editNickname.setVisibility(View.VISIBLE);
                    binding.applyNickname.setVisibility(View.VISIBLE);
                }
            });

            binding.applyNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = String.valueOf(binding.editNickname.getText());

                    if (!username.trim().isEmpty()) {
                        username = username.trim();
                        userCurrent.child(user.getUid()).child("nickname").setValue(username);

                        binding.editNickname.setVisibility(View.GONE);
                        binding.applyNickname.setVisibility(View.GONE);

                        binding.nickname.setText(username);

                        binding.nickname.setVisibility(View.VISIBLE);
                        binding.changeNickname.setVisibility(View.VISIBLE);

                        Toast.makeText(getContext(), "Имя пользователя изменено", Toast.LENGTH_SHORT).show();
                    }
                    else { Toast.makeText(getContext(), "Введите имя пользователя", Toast.LENGTH_SHORT).show(); }
                }
            });

            binding.changeBios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.changingBios.setText("");

                    binding.bios.setVisibility(View.GONE);
                    binding.changeBios.setVisibility(View.GONE);

                    binding.changingBios.setVisibility(View.VISIBLE);
                    binding.applyBios.setVisibility(View.VISIBLE);
                }
            });

            binding.applyBios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bios = String.valueOf(binding.changingBios.getText());

                    if (!bios.trim().isEmpty()) {
                        bios = bios.trim();
                        userCurrent.child(user.getUid()).child("bios").setValue(bios);

                        binding.changingBios.setVisibility(View.GONE);
                        binding.applyBios.setVisibility(View.GONE);

                        binding.bios.setText(bios);

                        binding.bios.setVisibility(View.VISIBLE);
                        binding.changeBios.setVisibility(View.VISIBLE);

                        Toast.makeText(getContext(), "Описание изменено", Toast.LENGTH_SHORT).show();
                    }
                    else { Toast.makeText(getContext(), "Введите описание", Toast.LENGTH_SHORT).show(); }
                }
            });

            binding.logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Navigation.findNavController(root).navigate(R.id.action_navigation_profile_to_authorizationFragment);
                }
            });

            binding.profileIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImagePicker();
                }
            });
            // Получение пути к файлу в Firebase Storage
            String avatarPath = "avatars/" + user.getUid() + ".jpg";
            StorageReference avatarRef = storageRef.child(avatarPath);

            imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                Uri imageUri = result.getData().getData();

                                // Загрузка файла в Firebase Storage
                                UploadTask uploadTask = avatarRef.putFile(imageUri);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Получение URL-адреса загруженной аватарки
                                        avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri downloadUri) {
                                                // Сохранение URL-адреса в базе данных
                                                userCurrent.child(user.getUid()).child("avatarUrl").setValue(downloadUri.toString());

                                                // установка аватарки
                                                binding.profileIcon.setImageURI(imageUri);
                                                Toast.makeText(requireContext(), "Avatar changed successfully",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Обработка ошибки загрузки аватарки
                                        // ну ошибка и ошибка, чо бухтеть то
                                    }
                                });
                            }
                        }
                    });
        }



        return root;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    /*@Override
    public void onResume() {
        super.onResume();

        // Регистрация NetworkCallback
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Отмена регистрации NetworkCallback
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}