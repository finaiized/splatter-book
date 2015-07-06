package com.example.finaiized.splatterbook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.finaiized.splatterbook.R;
import com.example.finaiized.splatterbook.fragments.EditRecipeFragment;

public class EditActivity extends AppCompatActivity {

    public static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if (savedInstanceState == null) {
            EditRecipeFragment detail = EditRecipeFragment.newInstance(getIntent().getIntExtra(KEY_ID, 0));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, detail).commit();
        }
    }
}
