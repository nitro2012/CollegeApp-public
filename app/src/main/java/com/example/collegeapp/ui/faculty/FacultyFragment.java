package com.example.collegeapp.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeapp.Faculty.TeacherAdapter;
import com.example.collegeapp.Faculty.TeacherData;
import com.example.collegeapp.Faculty.UpdateFaculty;
import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {
    private RecyclerView csDepartment,mechanicalDepartment,civilDepartment,electricalDepartment;
    private LinearLayout csNoData,mechNoData,civilNoData,electricalNoData;
    private List<TeacherData> list1,list2,list3,list4;
    private DatabaseReference reference,dbRef;
    private FacultyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_faculty, container, false);
        csDepartment=view.findViewById(R.id.csDepartment);
        mechanicalDepartment=view.findViewById(R.id.mechDepartment);
        civilDepartment =view.findViewById(R.id.civilDepartment);
        electricalDepartment=view.findViewById(R.id.electricalDepartment);
        csNoData=view.findViewById(R.id.csNoData);
        civilNoData=view.findViewById(R.id.civilNoData);
        mechNoData=view.findViewById(R.id.mechNoData);
        electricalNoData=view.findViewById(R.id.electricalNoData);

        reference= Utils.getDatabase().getReference().child("Teacher");
        csDepartment();
        mechanicalDepartment();
        civilDepartment();
        electricalDepartment();
        return view;
    }
    private void csDepartment() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if(!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }
                else
                {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dsnapshot:snapshot.getChildren()){
                        TeacherData data=dsnapshot.getValue(TeacherData.class);


                        list1.add(data);
                    }


                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter=new FacultyAdapter(list1,getActivity(),"Computer Science");
                    csDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mechanicalDepartment() {
        dbRef=reference.child("Mechanical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if(!snapshot.exists()){
                    mechNoData.setVisibility(View.VISIBLE);
                    mechanicalDepartment.setVisibility(View.GONE);
                }
                else
                {
                    mechNoData.setVisibility(View.GONE);
                    mechanicalDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dsnapshot:snapshot.getChildren()){
                        TeacherData data=dsnapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    mechanicalDepartment.setHasFixedSize(true);
                    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter=new FacultyAdapter(list2,getActivity(),"Mechanical");
                    mechanicalDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void civilDepartment() {
        dbRef=reference.child("Civil");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if(!snapshot.exists()){
                    civilNoData.setVisibility(View.VISIBLE);
                    civilDepartment.setVisibility(View.GONE);
                }
                else
                {
                    civilNoData.setVisibility(View.GONE);
                    civilDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dsnapshot:snapshot.getChildren()){
                        TeacherData data=dsnapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    civilDepartment.setHasFixedSize(true);
                    civilDepartment.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter=new FacultyAdapter(list3,getActivity(),"Civil");
                    civilDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void electricalDepartment() {
        dbRef=reference.child("Electrical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if(!snapshot.exists()){
                    electricalNoData.setVisibility(View.VISIBLE);
                    electricalDepartment.setVisibility(View.GONE);
                }
                else
                {
                    electricalNoData.setVisibility(View.GONE);
                    electricalDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dsnapshot:snapshot.getChildren()){
                        TeacherData data=dsnapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    electricalDepartment.setHasFixedSize(true);
                    electricalDepartment.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter=new FacultyAdapter(list4,getActivity(),"Electrical");
                    electricalDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}