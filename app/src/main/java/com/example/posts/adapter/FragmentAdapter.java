package com.example.posts.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.posts.ui.CinemaFrag;
import com.example.posts.ui.MovieFrag;

public class FragmentAdapter extends FragmentPagerAdapter {
    int count ;
    public FragmentAdapter(@NonNull FragmentManager fm, int behavior , int tabLayoutCount) {
        super(fm, behavior);
        this.count = tabLayoutCount ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 :{
                return new MovieFrag();}

            case 1 :{
                return new CinemaFrag();
            }
            default:
                return null ;

        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
