package com.example.finaiized.splatterbook.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.persistence.RecipesContract.Recipes;

public class RecipeDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String KEY_INDEX = "index";

    private TextView recipeNameText;
    private TextView recipeDescriptionText;

    private OnEditRequestedListener editRequestedListener;

    public interface OnEditRequestedListener {
        void editRequested(int id);
    }

    public static RecipeDetailFragment newInstance(int index) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle b = new Bundle();
        b.putInt(KEY_INDEX, index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editRequestedListener = (OnEditRequestedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement the interfaces declared in " + getClass().getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                editRequestedListener.editRequested(getCurrentIndex());
                return true;
            case R.id.action_delete:
                deleteRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteRecipe() {
        Uri uri = Recipes.buildRecipeUri(String.valueOf(getCurrentIndex()));
        getActivity().getContentResolver().delete(uri, null, null);
        getFragmentManager().popBackStack();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        recipeNameText = (TextView) v.findViewById(R.id.recipe_title);
        recipeDescriptionText = (TextView) v.findViewById(R.id.recipe_description);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    public int getCurrentIndex() {
        return getArguments().getInt(KEY_INDEX, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {Recipes.ID, Recipes.TITLE, Recipes.DESCRIPTION};
        Uri uri = Recipes.buildRecipeUri(String.valueOf(getCurrentIndex()));
        return new CursorLoader(getActivity(), uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            data.moveToFirst();
            String title = data.getString(data.getColumnIndex(Recipes.TITLE));
            String description = data.getString(data.getColumnIndex(Recipes.DESCRIPTION));
            recipeNameText.setText(title);
            recipeDescriptionText.setText(description);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void updateRecipeView(int id) {
        getArguments().putInt(KEY_INDEX, id);
        getLoaderManager().restartLoader(0, null, this);
    }
}
