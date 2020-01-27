package com.example.dietervanonckelenmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_detailfragment, container, false);
    }
}