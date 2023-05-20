package com.example.gymfinder.ui.addExercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfinder.TrainingItem;
import com.example.gymfinder.databinding.FragmentExercisesBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private boolean isConnected;

    FragmentExercisesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference exercise = database.getReference("exercise");

    // хранение всех тренировок
    ArrayList<ExerciseItem> exercises = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recExercises.setLayoutManager(new LinearLayoutManager(getContext()));
        ExerciseAdapter adapter = new ExerciseAdapter(exercises);
        binding.recExercises.setAdapter(adapter);


        /*String exerciseId = exercise.push().getKey();
        ExerciseItem item1 = new ExerciseItem(0,"Отжимания", "Грудные мыщцы");
        exercise.child(exerciseId).setValue(item1);*/

        /*
        ExerciseItem item11 = new ExerciseItem(1, "Жим лёжа", "Грудные мыщцы");
        ExerciseItem item12 = new ExerciseItem(2, "Жим лёжа гантелями", "Грудные мыщцы");
        ExerciseItem item13 = new ExerciseItem(3, "Разведение рук с гантелями", "Грудные мыщцы");
        ExerciseItem item21 = new ExerciseItem(4, "Подтягивания", "Широчайшие мыщцы");
        ExerciseItem item22 = new ExerciseItem(5, "Вертикальная тяга широчайшими", "Широчайшие мыщцы");
        ExerciseItem item3 = new ExerciseItem(6, "Гиперэкстензия", "Столбовидные мыщцы");
        ExerciseItem item4 = new ExerciseItem(7, "Скручивания", "Брюшной пресс");
        ExerciseItem item51 = new ExerciseItem(8, "Жим сидя над головой", "Плечи");
        ExerciseItem item52 = new ExerciseItem(9, "Жим Арнольда", "Плечи");
        ExerciseItem item61 = new ExerciseItem(10, "21 сгибание", "Бицепс");
        ExerciseItem item62 = new ExerciseItem(11, "Молотки", "Бицепс");
        ExerciseItem item63 = new ExerciseItem(12, "Сгибание со штангой", "Бицепс");
        exercises.add(item1);
        exercises.add(item11);
        exercises.add(item12);
        exercises.add(item13);
        exercises.add(item21);
        exercises.add(item22);
        exercises.add(item3);
        exercises.add(item4);
        exercises.add(item51);
        exercises.add(item52);
        exercises.add(item61);
        exercises.add(item62);
        exercises.add(item63);
        exercise.setValue(exercises);*/

        exercise.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Получаем новое сообщение
                ExerciseItem tmp = dataSnapshot.getValue(ExerciseItem.class);
                exercises.add(tmp);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        ValueEventListener exerciseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ExerciseItem message = dataSnapshot.getValue(ExerciseItem.class);
                    exercises.add(message);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибки получения значения
                Toast.makeText(getContext(), "Значения не получены", Toast.LENGTH_SHORT).show();
            }
        };

        // listener на узел информации текущего пользователя
        exercise.addListenerForSingleValueEvent(exerciseListener);

        // Инициализация ConnectivityManager
        connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Перенос прогресс бара на передний план
        binding.progressBar.bringToFront();

        // Инициализация NetworkCallback
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                isConnected = true;
                // Обработка доступности интернет-соединения
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onLost(@NonNull Network network) {
                isConnected = false;
                // Обработка потери интернет-соединения
                // Отображение загрузочного кольца
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        return root;
    }

    @Override
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
