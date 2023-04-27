package com.example.gymfinder.ui.currentTraining;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.gymfinder.R;

public class CurrentTrainingViewModel extends ViewModel {

    public void endTraining() {
        // Здесь мне необходимо прописать сохранение моих тренировок и вывод оной в рекуклер
        ;
    }

    public void returnBack(android.view.View v) {
        Navigation.findNavController(v).navigate(R.id.action_currentTrainingFragment_to_navigation_training2);
    }
}
