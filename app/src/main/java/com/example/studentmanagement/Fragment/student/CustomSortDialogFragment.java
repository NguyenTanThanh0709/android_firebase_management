package com.example.studentmanagement.Fragment.student;

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

    private RadioButton radioButtonSortByName, radioButtonSortByDate, radioButtonSortBystartlearn, radioButtonSortByGPA;
    private RadioButton radioButtonSortByName_, radioButtonSortByDate_, radioButtonSortBystartlearn_, radioButtonSortByGPA_;

    public interface SortDialogListener {
        void onSortApplied( boolean sortByName,boolean sortByDate,boolean sortByGPA, boolean sortByStartLearn
            , boolean sortByName_,boolean sortByDate_,boolean sortByGPA_, boolean sortByStartLearn_
        );
    }

    private SortDialogListener sortDialogListener;

    public void setSortDialogListener(SortDialogListener listener) {
        this.sortDialogListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        View view = requireActivity().getLayoutInflater().inflate(R.layout.custom_sort_dialog, null);

        radioButtonSortByName = view.findViewById(R.id.radioButtonSortByName);
        radioButtonSortByDate = view.findViewById(R.id.radioButtonSortByDate);
        radioButtonSortByGPA = view.findViewById(R.id.radioButtonSortByGPA);
        radioButtonSortBystartlearn = view.findViewById(R.id.radioButtonSortBystartlearn);


        radioButtonSortByName_ = view.findViewById(R.id.radioButtonSortByName_);
        radioButtonSortByDate_ = view.findViewById(R.id.radioButtonSortByDate_);
        radioButtonSortByGPA_ = view.findViewById(R.id.radioButtonSortByGPA_);
        radioButtonSortBystartlearn_ = view.findViewById(R.id.radioButtonSortBystartlearn_);
        builder.setView(view)
                .setTitle("Sắp xếp học sinh theo tiêu chí!")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (sortDialogListener != null) {
                            sortDialogListener.onSortApplied(
                                    radioButtonSortByName.isChecked(),
                                    radioButtonSortByDate.isChecked(),
                                    radioButtonSortByGPA.isChecked(),
                                    radioButtonSortBystartlearn.isChecked(),
                                    radioButtonSortByName_.isChecked(),
                                    radioButtonSortByDate_.isChecked(),
                                    radioButtonSortByGPA_.isChecked(),
                                    radioButtonSortBystartlearn_.isChecked()
                            );
                        }
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
