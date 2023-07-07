package com.example.collegeapp.ui.department;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.collegeapp.R;

import java.util.ArrayList;
import java.util.List;


public class DepartmentFragment extends Fragment {

    View dept_fragment;

    DeptComputerScienceFragment deptComputerScienceFragmentAction;
    DeptElectricalFragment deptElectricalFragmentAction;
    DeptMechanicalFragment deptMechanicalFragmentAction;
    DeptCivilFragment deptCivilFragmentAction;
    Spinner spinner;
    List<String> departments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dept_fragment = inflater.inflate(R.layout.fragment_department, container, false);

        spinner = dept_fragment.findViewById(R.id.department_spinner);
        deptComputerScienceFragmentAction = new DeptComputerScienceFragment();
        deptElectricalFragmentAction = new DeptElectricalFragment();
        deptMechanicalFragmentAction = new DeptMechanicalFragment();
        deptCivilFragmentAction = new DeptCivilFragment();

        departments = new ArrayList<String>();
        departments.add("Computer Science");
        departments.add("Electrical Engineering");
        departments.add("Mechanical Engineering");
        departments.add("Civil Engineering");

        ArrayAdapter<String> deptAdapter =new ArrayAdapter<String>(getContext(), R.layout.spinner_item, departments);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(deptAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        selectFragment(deptComputerScienceFragmentAction);
                        break;
                    case 1:
                        selectFragment(deptElectricalFragmentAction);
                        break;
                    case 2:
                        selectFragment(deptMechanicalFragmentAction);
                        break;
                    case 3:
                        selectFragment(deptCivilFragmentAction);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return dept_fragment;
    }

    private void selectFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dept_frame_layout, fragment);
        fragmentTransaction.commit();

    }
}