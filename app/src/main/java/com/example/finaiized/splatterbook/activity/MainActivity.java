package com.example.finaiized.splatterbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.fragments.EditRecipeFragment;
import com.example.finaiized.splatterbook.fragments.RecipeDetailFragment;
import com.example.finaiized.splatterbook.fragments.RecipeListFragment;


public class MainActivity extends AppCompatActivity implements
        RecipeListFragment.OnAddRecipeListener,
        RecipeListFragment.OnRecipeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new RecipeListFragment()).commit();
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_secondary);

        if (fragment != null && fragment instanceof RecipeDetailFragment) {
            ((RecipeDetailFragment) fragment).updateRecipeView(id);
        } else {
            fragment = RecipeDetailFragment.newInstance(id);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
