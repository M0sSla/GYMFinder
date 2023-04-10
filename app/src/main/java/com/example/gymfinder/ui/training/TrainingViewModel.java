package com.example.gymfinder.ui.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> textButton;

    public TrainingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Быстрый старт");
        textButton = new MutableLiveData<>();
        textButton.setValue("Новая тренировка");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getTextButton() {
        return textButton;
    }
}