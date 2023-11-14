package com.example.studentmanagement.ui.tab1;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.databinding.FragmentSlideshowBinding;
import com.example.studentmanagement.databinding.FragmentTab1Binding;
import com.example.studentmanagement.ui.slideshow.SlideshowViewModel;

public class Tab1Fragment extends Fragment {

    private FragmentTab1Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Tab1ViewModel tab1ViewModel =
                new ViewModelProvider(this).get(Tab1ViewModel.class);

        binding = FragmentTab1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTab1;
        tab1ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}