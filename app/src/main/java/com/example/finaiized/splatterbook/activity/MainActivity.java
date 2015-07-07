package com.example.finaiized.splatterbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.fragments.EditRecipeFragment;
import com.example.finaiized.splatterbook.fragments.RecipeDetailFragment;
import com.example.finaiized.splatterbook.fragments.RecipeListFragment;


public class MainActivity extends AppCompatActivity implements
        RecipeListFragment.OnAddRecipeListener,
        RecipeListFragment.OnRecipeSelectedListener,
        RecipeDetailFragment.OnEditRequestedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new RecipeListFragment()).commit();
        }
    }

    @Override
    public void onAddRecipe() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_secondary);
        int fragmentId = R.id.fragment_container;
        if (fragment != null) {
            fragmentId = R.id.fragment_secondary;
        }
        fragment = EditRecipeFragment.newInstance(-1);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(fragmentId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onRecipeSelected(int id) {
        View v = findViewById(R.id.fragment_secondary);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_secondary);

        if (v != null) { // Dual pane
            if (fragment != null && fragment instanceof RecipeDetailFragment) { // Detail fragment already exists
                ((RecipeDetailFragment) fragment).updateRecipeView(id);
            } else { // No detail fragment from before - create a new one
                fragment = RecipeDetailFragment.newInstance(id);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_secondary, fragment).commit();
            }
        } else { // Single pane - replace existing fragment
            fragment = RecipeDetailFragment.newInstance(id);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void editRequested(int id) {
        int viewId = R.id.fragment_container;
        View v = findViewById(R.id.fragment_secondary);
        if (v != null) viewId = R.id.fragment_secondary;

        Fragment fragment = EditRecipeFragment.newInstance(id);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(viewId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
