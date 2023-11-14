package com.example.studentmanagement.ui.tab2;

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
import com.example.studentmanagement.databinding.FragmentTab2Binding;
import com.example.studentmanagement.ui.slideshow.SlideshowViewModel;

public class Tab2Fragment extends Fragment {

    private FragmentTab2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Tab2ViewModel tab2ViewModel =
                new ViewModelProvider(this).get(Tab2ViewModel.class);

        binding = FragmentTab2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTab2;
        tab2ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}