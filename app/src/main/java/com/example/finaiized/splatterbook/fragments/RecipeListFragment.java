package com.example.finaiized.splatterbook.fragments;

import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.widget.SimpleCursorAdapter;

import com.example.finaiized.splatterbook.persistence.RecipesContract;

public class RecipeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null,
                new String[] {RecipesContract.Recipes.TITLE, RecipesContract.Recipes.DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {RecipesContract.Recipes.ID, RecipesContract.Recipes.TITLE, RecipesContract.Recipes.DESCRIPTION};
        return new CursorLoader(getActivity(), RecipesContract.Recipes.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
