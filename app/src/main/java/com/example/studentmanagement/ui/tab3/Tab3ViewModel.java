package com.example.studentmanagement.ui.tab3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Tab3ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Tab3ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is t3 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}