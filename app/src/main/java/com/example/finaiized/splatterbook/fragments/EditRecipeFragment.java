package com.example.finaiized.splatterbook.fragments;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.persistence.RecipesContract;

public class EditRecipeFragment extends Fragment {

    private static final String KEY_ID = "id";

    private EditText recipeNameText;
    private EditText recipeDescriptionText;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                saveRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        recipeNameText = (EditText) v.findViewById(R.id.recipe_name);
        recipeDescriptionText = (EditText) v.findViewById(R.id.recipe_description);
        return v;
    }

    private void saveRecipe() {
        // TODO: Content validation
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(RecipesContract.Recipes.TITLE, recipeNameText.getText().toString());
        recipeValues.put(RecipesContract.Recipes.DESCRIPTION, recipeDescriptionText.getText().toString());

        if (getCurrentIndex() == -1) {
            getActivity().getContentResolver().insert(RecipesContract.Recipes.CONTENT_URI, recipeValues);
        } else {
            Uri uri = RecipesContract.Recipes.buildRecipeUri(String.valueOf(getCurrentIndex()));
            getActivity().getContentResolver().update(uri, recipeValues, null, null);
        }

        getFragmentManager().popBackStack();
    }

    public int getCurrentIndex() {
        return getArguments().getInt(KEY_ID);
    }
}
