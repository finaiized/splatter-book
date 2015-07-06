package com.example.finaiized.splatterbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finaiized.splatterbook.R;

public class EditRecipeFragment extends Fragment {

    private static final String KEY_ID = "id";

    /**
     * 
     * @param id The id of the recipe to edit, or -1 for a (new) empty recipe
     */
    public static EditRecipeFragment newInstance(int id) {
        EditRecipeFragment fragment = new EditRecipeFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }
}
