package com.example.studentmanagement.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.studentmanagement.R;

import java.util.Arrays;
import java.util.List;

public class SearchDialogFragment extends DialogFragment {

    private EditText editTextSearchByName, editTextSearchByPhone, editTextSearchByEmail, editTextSearchByStartLearn;
    private Spinner spinnerSearchByClass;

    public interface SearchDialogListener {
        void onSearchApplied(String searchByName, String searchByPhone, String searchByEmail, String selectedClass, String searchByStartLearn);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        View view = requireActivity().getLayoutInflater().inflate(R.layout.custom_search_dialog, null);

        editTextSearchByName = view.findViewById(R.id.SearchByName);
        editTextSearchByPhone = view.findViewById(R.id.SearchByPhone);
        editTextSearchByEmail = view.findViewById(R.id.SearchByEmail);
        editTextSearchByStartLearn = view.findViewById(R.id.SearchByStartLearn);
        spinnerSearchByClass = view.findViewById(R.id.spinnerSearchByClass);


        // Assuming you have a list of class options
        List<String> classOptions = Arrays.asList("Class A", "Class B", "Class C");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, classOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearchByClass.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Search Options")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
