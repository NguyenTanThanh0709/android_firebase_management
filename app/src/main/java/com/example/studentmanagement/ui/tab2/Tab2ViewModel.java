package com.example.studentmanagement.ui.tab2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Tab2ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Tab2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is t2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}