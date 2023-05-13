package com.example.gymfinder.ui.currentTraining;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.gymfinder.DataBase.TrainingDAO;
import com.example.gymfinder.DataBase.TrainingDB;
import com.example.gymfinder.R;
import com.example.gymfinder.TrainingItem;
import com.example.gymfinder.databinding.ActivityMainBinding;
import com.example.gymfinder.databinding.FragmentCurrentTrainingBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CurrentTrainingViewModel extends ViewModel {
    Disposable newTrainingDisposable;
    private NavController navController;

    public void setNavController(NavController nav) {
        navController = nav;
    }
    public void endTraining(android.view.View v, FragmentCurrentTrainingBinding binding) {
        TrainingDB trainingDB = TrainingDB.getInstance(v.getContext());
        TrainingDAO trainingDAO = trainingDB.TrainingDAO();
        // Где это диспоз диспозить?
        newTrainingDisposable = trainingDAO.addTraining(new TrainingItem(
                Integer.parseInt(binding.duration.getText().toString()),
                Integer.parseInt(binding.volume.getText().toString()),
                "Viacheslav",
                "some information",
                30
        )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::returnBack);
        // Здесь мне необходимо прописать сохранение моих тренировок и вывод оной в рекуклер
    }


    public void returnBack() {
        navController.navigate(R.id.action_currentTrainingFragment_to_navigation_training2);
    }
}
