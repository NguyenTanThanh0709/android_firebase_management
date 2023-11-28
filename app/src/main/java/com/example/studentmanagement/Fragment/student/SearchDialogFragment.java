package com.example.studentmanagement.Fragment.student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchDialogFragment extends DialogFragment {


    private CheckBox checkBoxDefaultSearch;
    private EditText editTextSearchByName, editTextSearchByPhone, editTextSearchByEmail, editTextSearchByStartLearn, editTextSearchByBirthDay;
    private Spinner spinnerSearchByClass;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private List<Class_> classList;

    public void setClassList(List<Class_> classList) {
        this.classList = classList;
    }

    public interface SearchDialogListener {
        void onSearchApplied(Boolean checkbox, String searchByName, String searchByPhone, String searchByEmail, String selectedClass, String searchByStartLearn, String searchByBirthDay, Boolean gender);
    }

    private SearchDialogListener searchDialogListener;

    public void setSearchDialogListener(SearchDialogListener listener) {
        this.searchDialogListener = listener;
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
        editTextSearchByBirthDay = view.findViewById(R.id.SearchByBirthDay);
        spinnerSearchByClass = view.findViewById(R.id.spinnerSearchByClass);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);

        checkBoxDefaultSearch= view.findViewById(R.id.checkBoxDefaultSearch);

        List<String> classOptions = new ArrayList<>();
        classOptions.add("tìm theo lớp");
        for(Class_ class_: classList){
            classOptions.add(class_.getName());
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, classOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearchByClass.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Tìm kiếm sinh viên theo nhiều tiêu chí")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                        Boolean isMale = null;
                        if (selectedGenderId == radioButtonMale.getId()) {
                            isMale = true;
                        } else if (selectedGenderId == radioButtonFemale.getId()) {
                            isMale = false;
                        }
                        Boolean checkbox = checkBoxDefaultSearch.isChecked();
                        String searchByName = editTextSearchByName.getText().toString();
                        String searchByPhone = editTextSearchByPhone.getText().toString();
                        String searchByEmail = editTextSearchByEmail.getText().toString();
                        String selectedClass = spinnerSearchByClass.getSelectedItem().toString();
                        String searchByStartLearn = editTextSearchByStartLearn.getText().toString();
                        String searchByBirthDay = editTextSearchByBirthDay.getText().toString();
                        if (searchDialogListener != null) {
                            searchDialogListener.onSearchApplied(
                                    checkbox,
                                    searchByName,
                                    searchByPhone,
                                    searchByEmail,
                                    selectedClass,
                                    searchByStartLearn,
                                    searchByBirthDay,
                                    isMale
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
