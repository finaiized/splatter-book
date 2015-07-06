package com.example.finaiized.splatterbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.fragments.RecipeDetailFragment;
import com.example.finaiized.splatterbook.fragments.RecipeListFragment;


public class MainActivity extends AppCompatActivity implements
        RecipeListFragment.OnAddRecipeListener,
        RecipeListFragment.OnRecipeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appBar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new RecipeListFragment()).commit();
    }

    @Override
    public void onAddRecipe() {

    }

    @Override
    public void onRecipeSelected(int id) {
        RecipeDetailFragment detailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_details);
        if (detailFragment != null && detailFragment.getCurrentIndex() != id) {
            detailFragment.updateRecipeView(id);
        } else {
            detailFragment = RecipeDetailFragment.newInstance(id);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.fragment_container, detailFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
