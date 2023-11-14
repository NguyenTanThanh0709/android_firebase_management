package com.example.studentmanagement.ui.tab3;

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
import com.example.studentmanagement.databinding.FragmentTab3Binding;
import com.example.studentmanagement.ui.slideshow.SlideshowViewModel;

public class Tab3Fragment extends Fragment {

    private FragmentTab3Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Tab3ViewModel tab3ViewModel =
                new ViewModelProvider(this).get(Tab3ViewModel.class);

        binding = FragmentTab3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTab3;
        tab3ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}