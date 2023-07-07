package com.example.collegeapp.ui.home;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutAdapter extends FragmentStateAdapter {
    public TabLayoutAdapter(@NonNull HomeFragment fragmentActivity) {super(fragmentActivity);}

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new NewsHomeFragment();
            case 2:
                return new BlogHomeFragment();
            default:
                return new LoginHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
