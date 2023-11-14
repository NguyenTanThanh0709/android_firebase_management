package com.example.studentmanagement.ui.tab1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Tab1ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Tab1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ta1 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}