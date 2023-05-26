package com.example.gymfinder.ui.chat;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<String>  mText;

    public ChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Здесь могла быть ваша реклама :D");
    }
    public LiveData<String> getText() { return mText; }
}
