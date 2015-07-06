package com.example.finaiized.splatterbook.fragments;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.activity.DetailActivity;
import com.example.finaiized.splatterbook.activity.EditActivity;
import com.example.finaiized.splatterbook.persistence.RecipesContract;

public class RecipeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter adapter;
    boolean dualPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null,
                new String[] {RecipesContract.Recipes.TITLE, RecipesContract.Recipes.DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

        View detailsFrame = getActivity().findViewById(R.id.recipe_details);
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (dualPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showRecipeDetails(0);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(getActivity(), EditActivity.class);
                i.putExtra(EditActivity.KEY_ID, -1);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showRecipeDetails((int)id);
    }

    private void showRecipeDetails(int position) {
        if (dualPane) {
            getListView().setItemChecked(position, true);
            RecipeDetailFragment fragment = (RecipeDetailFragment) getFragmentManager().findFragmentById(R.id.recipe_details);
            if (fragment == null || fragment.getCurrentIndex() != position) {
                fragment = RecipeDetailFragment.newInstance(position);
                assert(fragment.getArguments() != null);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.recipe_details, fragment);
                ft.commit();
            }
        } else {
            Intent i = new Intent(getActivity(), DetailActivity.class);
            i.putExtra(DetailActivity.KEY_INDEX, position);
            startActivity(i);
        }
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
