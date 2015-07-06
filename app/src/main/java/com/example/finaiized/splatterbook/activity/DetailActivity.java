package com.example.finaiized.splatterbook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.finaiized.splatterbook.fragments.RecipeDetailFragment;

public class DetailActivity extends AppCompatActivity {
    public static final String KEY_INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            RecipeDetailFragment detail = RecipeDetailFragment.newInstance(getIntent().getIntExtra(KEY_INDEX, 0));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, detail).commit();
        }
    }
}
