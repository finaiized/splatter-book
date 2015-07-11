package com.example.finaiized.splatterbook.fragments;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.persistence.RecipesContract;

public class EditRecipeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, getArguments(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        recipeNameText = (EditText) v.findViewById(R.id.recipe_name);
        recipeDescriptionText = (EditText) v.findViewById(R.id.recipe_description);

        Button addIngredient = (Button) v.findViewById(R.id.add_ingredient);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddIngredientDialogFragment().show(getFragmentManager(), null);
            }
        });

        return v;
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (args != null && getCurrentIndex() == -1) return null; // A new recipe is being created, so no cursor is necessary
        String[] projection = new String[] {RecipesContract.Recipes.ID, RecipesContract.Recipes.TITLE, RecipesContract.Recipes.DESCRIPTION};
        Uri uri = RecipesContract.Recipes.buildRecipeUri(String.valueOf(getCurrentIndex()));
        return new CursorLoader(getActivity(), uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            data.moveToFirst();
            String title = data.getString(data.getColumnIndex(RecipesContract.Recipes.TITLE));
            String description = data.getString(data.getColumnIndex(RecipesContract.Recipes.DESCRIPTION));
            recipeNameText.setText(title);
            recipeDescriptionText.setText(description);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
