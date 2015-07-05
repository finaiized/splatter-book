package com.example.finaiized.splatterbook.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe_data.db";
    private static final int DATABASE_VERSION = 1;

    public RecipeOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Table.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
