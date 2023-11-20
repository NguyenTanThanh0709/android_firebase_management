package com.example.studentmanagement.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.studentmanagement.R;

public class CustomSortDialogFragment extends DialogFragment {

    private RadioButton radioButtonSortByName, radioButtonSortByDate, radioButtonSortBystartlearn;

    public interface SortDialogListener {
        void onSortApplied(boolean sortByDate, boolean sortByName, boolean sortByStartLearn);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        View view = requireActivity().getLayoutInflater().inflate(R.layout.custom_sort_dialog, null);

        radioButtonSortByName = view.findViewById(R.id.radioButtonSortByName);
        radioButtonSortByDate = view.findViewById(R.id.radioButtonSortByDate);
        radioButtonSortBystartlearn = view.findViewById(R.id.radioButtonSortBystartlearn);

        builder.setView(view)
                .setTitle("Sort Students")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        SortDialogListener listener = (SortDialogListener) requireActivity();
//                        listener.onSortApplied(
//                                radioButtonSortByDate.isChecked(),
//                                radioButtonSortByName.isChecked(),
//                                radioButtonSortBystartlearn.isChecked());
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
