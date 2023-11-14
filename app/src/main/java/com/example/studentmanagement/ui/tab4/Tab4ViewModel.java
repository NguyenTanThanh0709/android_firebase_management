package com.example.studentmanagement.ui.tab4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Tab4ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Tab4ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is t4 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}