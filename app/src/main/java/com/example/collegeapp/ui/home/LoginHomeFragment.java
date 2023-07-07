package com.example.collegeapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.collegeapp.LoginPage;
import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginHomeFragment extends Fragment {


    private int[] images;
    private SliderAdapter sladapter;
    private SliderView sliderView;
    private CardView admincard;
    private CardView facultycard;
    private CardView studentcard;

    public LoginHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_home, container, false);
        sliderView = view.findViewById(R.id.image_slider);

        images = new int[]{R.drawable.slider_5, R.drawable.slider_6, R.drawable.slider_7, R.drawable.slider_8};
        sladapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sladapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.startAutoCycle();


        admincard = view.findViewById(R.id.admin_login);
        facultycard = view.findViewById(R.id.faculty_login);
        studentcard = view.findViewById(R.id.student_login);

        admincard.setOnClickListener(view1 -> {
            Intent ai=new Intent(getContext(), LoginPage.class);
            MainActivity.userType="Admin";
            ai.putExtra("name","Admin");
            startActivity(ai);
        });

        facultycard.setOnClickListener(view12 -> {

            Intent fi=new Intent(getContext(),LoginPage.class);
            MainActivity.userType="Faculty";
            fi.putExtra("name","Faculty");
            startActivity(fi);
        });

        studentcard.setOnClickListener(view13 -> {

            Intent si=new Intent(getContext(),LoginPage.class);
            MainActivity.userType="Student";
            si.putExtra("name","Student");
            startActivity(si);
        });

        return view;
    }
}