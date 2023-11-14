package com.example.studentmanagement.ui.tab4;

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
import com.example.studentmanagement.databinding.FragmentTab4Binding;
import com.example.studentmanagement.ui.slideshow.SlideshowViewModel;

public class Tab4Fragment extends Fragment {

    private FragmentTab4Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Tab4ViewModel tab4ViewModel =
                new ViewModelProvider(this).get(Tab4ViewModel.class);

        binding = FragmentTab4Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTab4;
        tab4ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}