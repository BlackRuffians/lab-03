package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    // Listener interface for callback
    public interface EditCityDialogListener {
        void editCity(int position, City city);
    }

    private EditCityDialogListener listener;
    private int position;
    private String currentCity;
    private String currentProvince;

    // Factory method to create an instance with arguments
    public static EditCityFragment newInstance(int position, String city, String province) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("city", city);
        args.putString("province", province);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            currentCity = getArguments().getString("city");
            currentProvince = getArguments().getString("province");
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);
        EditText editCityName = view.findViewById(R.id.edit_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_province_text);

        // Pre-fill existing values
        editCityName.setText(currentCity);
        editProvinceName.setText(currentProvince);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newCity = editCityName.getText().toString();
                    String newProvince = editProvinceName.getText().toString();
                    listener.editCity(position, new City(newCity, newProvince));
                })
                .create();
    }
}
