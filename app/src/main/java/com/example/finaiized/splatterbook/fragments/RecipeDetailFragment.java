package com.example.finaiized.splatterbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finaiized.splatterbook.R;

public class RecipeDetailFragment extends Fragment {
    private static final String KEY_INDEX = "index";


    public static RecipeDetailFragment newInstance(int index) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle b = new Bundle();
        b.putInt(KEY_INDEX, index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public int getCurrentIndex() {
        return getArguments().getInt(KEY_INDEX, 0);
    }
}
