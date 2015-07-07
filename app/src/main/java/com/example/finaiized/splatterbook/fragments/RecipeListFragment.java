package com.example.finaiized.splatterbook.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.persistence.RecipesContract;

public class RecipeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String KEY_DUAL_PANE = "dual_pane";
    SimpleCursorAdapter adapter;
    OnRecipeSelectedListener recipeSelectedListener;
    OnAddRecipeListener  addRecipeListener;
    private boolean dualPane = false;

    public interface OnRecipeSelectedListener {
        void onRecipeSelected(int id);
    }

    public interface OnAddRecipeListener {
        void onAddRecipe();
    }

    public static RecipeListFragment newInstance(boolean dualPane) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle b = new Bundle();
        b.putBoolean(KEY_DUAL_PANE, dualPane);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            recipeSelectedListener = (OnRecipeSelectedListener) activity;
            addRecipeListener = (OnAddRecipeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement the interfaces declared in " + getClass().getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dualPane = getArguments().getBoolean(KEY_DUAL_PANE);

        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_activated_2, null,
                new String[] {RecipesContract.Recipes.TITLE, RecipesContract.Recipes.DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(adapter);
        setActivatedOnItemClick(dualPane);
        getLoaderManager().initLoader(0, null, this);
    }

    private void setActivatedOnItemClick(boolean activated) {
        getListView().setChoiceMode(activated ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addRecipeListener.onAddRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getListView().setItemChecked(position, true);
        recipeSelectedListener.onRecipeSelected((int) id);
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
