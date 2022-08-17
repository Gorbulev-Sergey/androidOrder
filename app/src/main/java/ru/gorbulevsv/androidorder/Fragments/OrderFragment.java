package ru.gorbulevsv.androidorder.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.gorbulevsv.androidorder.MainActivity;
import ru.gorbulevsv.androidorder.R;

public class OrderFragment extends Fragment {
    Calendar calendar = Calendar.getInstance();
    Button buttonDate,buttonSelectOnMap;
    EditText editDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View orderFragment = inflater.inflate(R.layout.fragment_order, container, false);
        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
            editDate.setText(dateFormat.format(calendar.getTime()));
        };
        editDate = orderFragment.findViewById(R.id.editDate);
        buttonDate = orderFragment.findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(view -> {
            new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        buttonSelectOnMap=orderFragment.findViewById(R.id.buttonSelectOnMap);
        buttonSelectOnMap.setOnClickListener(view -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, MapFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        });

        return orderFragment;
    }
}