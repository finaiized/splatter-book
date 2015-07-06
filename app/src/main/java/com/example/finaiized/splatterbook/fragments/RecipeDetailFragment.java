package com.example.finaiized.splatterbook.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.persistence.RecipesContract;

public class RecipeDetailFragment extends Fragment {
    private static final String KEY_INDEX = "index";
    private TextView title;
    private TextView description;


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
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        title = (TextView) v.findViewById(R.id.recipe_title);
        description = (TextView) v.findViewById(R.id.recipe_description);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Uri uri = RecipesContract.Recipes.buildRecipeUri(String.valueOf(getCurrentIndex()));
        Cursor c = getActivity().getContentResolver().query(uri, new String[] {RecipesContract.Recipes.TITLE, RecipesContract.Recipes.DESCRIPTION}, null, null, null);
        if (c != null) {
            c.moveToNext();
            title.setText(c.getString(c.getColumnIndex(RecipesContract.Recipes.TITLE)));
            description.setText(c.getString(c.getColumnIndex(RecipesContract.Recipes.DESCRIPTION)));
        }
        c.close();
    }

    public int getCurrentIndex() {
        return getArguments().getInt(KEY_INDEX, 0);
    }
}
