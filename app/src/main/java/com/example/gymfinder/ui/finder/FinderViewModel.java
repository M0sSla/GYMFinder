package com.example.gymfinder.ui.finder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FinderViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FinderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a Finder fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
