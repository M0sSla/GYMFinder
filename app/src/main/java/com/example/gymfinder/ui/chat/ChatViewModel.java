package com.example.gymfinder.ui.chat;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<String>  mText;

    public ChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a chat fragment");
    }
    public LiveData<String> getText() { return mText; }
}
