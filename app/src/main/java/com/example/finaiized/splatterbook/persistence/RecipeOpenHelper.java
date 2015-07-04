package com.example.finaiized.splatterbook.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Dominic on 7/02/15.
 */
public class RecipeOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe_data.db";
    private static final int DATABASE_VERSION = 1;

    private static final String RECIPE_TABLE_NAME = "recipe";
    private static final String KEY_RECIPE_TITLE = "title";
    private static final String KEY_RECIPE_DESCRIPTION = "description";
    private static final String KEY_RECIPE_TIMES_MADE = "times_made";
    private static final String KEY_RECIPE_PREP_TIME = "prep_time";
    private static final String KEY_RECIPE_COOK_TIME = "cook_time";
    private static final String RECIPE_TABLE_CREATE = String.format(
        "CREATE TABLE %s (" +
            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT NOT NULL," +
            "%s TEXT," +
            "%s INTEGER," +
            "%s INTEGER," +
            "%s INTEGER" +
        ");",
            RECIPE_TABLE_NAME,
            BaseColumns._ID,
            KEY_RECIPE_TITLE,
            KEY_RECIPE_DESCRIPTION,
            KEY_RECIPE_TIMES_MADE,
            KEY_RECIPE_PREP_TIME,
            KEY_RECIPE_COOK_TIME);

    private static final String RECIPE_STEP_TABLE_NAME = "recipe_step";
    private static final String KEY_RECIPE_STEP_ID = "recipe_id";
    private static final String KEY_RECIPE_STEP_ORDER = "ordering";
    private static final String KEY_RECIPE_STEP_DESCRIPTION = "description";
    private static final String RECIPE_STEP_TABLE_CREATE = String.format(
        "CREATE TABLE %s (" +
                "%s INTEGER," +
                "%s INTEGER NOT NULL, " +
                "%s TEXT," +
                "FOREIGN KEY(%s) REFERENCES %s(%s)" +
        ");",
                RECIPE_STEP_TABLE_NAME,
                KEY_RECIPE_STEP_ID,
                KEY_RECIPE_STEP_ORDER,
                KEY_RECIPE_STEP_DESCRIPTION,
                KEY_RECIPE_STEP_ID, RECIPE_TABLE_NAME, BaseColumns._ID

    );

    private static final String RECIPE_INGREDIENT_TABLE_NAME = "recipe_ingredient";
    private static final String KEY_RECIPE_INGREDIENT_ID = "recipe_id";
    private static final String KEY_RECIPE_INGREDIENT_ORDER = "ordering";
    private static final String KEY_RECIPE_INGREDIENT_DESCRIPTION = "description";
    private static final String RECIPE_INGREDIENT_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER," +
                    "%s INTEGER NOT NULL," +
                    "%s TEXT," +
                    "FOREIGN KEY(%s) REFERENCES %s(%S)" +
                    ");",
            RECIPE_INGREDIENT_TABLE_NAME,
            KEY_RECIPE_INGREDIENT_ID,
            KEY_RECIPE_INGREDIENT_ORDER,
            KEY_RECIPE_INGREDIENT_DESCRIPTION,
            KEY_RECIPE_INGREDIENT_ID,
            RECIPE_TABLE_NAME, BaseColumns._ID
    );

    public RecipeOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RECIPE_TABLE_CREATE);
        db.execSQL(RECIPE_STEP_TABLE_CREATE);
        db.execSQL(RECIPE_INGREDIENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
