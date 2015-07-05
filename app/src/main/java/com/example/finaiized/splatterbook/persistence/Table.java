package com.example.finaiized.splatterbook.persistence;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class Table {

    public final class Recipes {
        public static final String TABLE_NAME = "recipe";
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String TIMES_MADE = "times_made";
        public static final String PREP_TIME = "prep_time";
        public static final String COOK_TIME = "cook_time";
        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TITLE + " TEXT NOT NULL," +
                        DESCRIPTION + " TEXT," +
                        TIMES_MADE + " INTEGER," +
                        PREP_TIME + " INTEGER," +
                        COOK_TIME + " INTEGER" +
                ");";
    }

    public final class Steps {
        public static final String TABLE_NAME = "recipe_step";
        public static final String RECIPE_ID = "recipe_id";
        public static final String ORDER = "ordering";
        public static final String DESCRIPTION = "description";
        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        RECIPE_ID + " INTEGER," +
                        ORDER + " INTEGER NOT NULL, " +
                        DESCRIPTION + " TEXT," +
                        "FOREIGN KEY(" + RECIPE_ID + ") REFERENCES " +
                        Recipes.TABLE_NAME + "(" + BaseColumns._ID + ")" +
                ");";

    }

    public final class Ingredients {
        public static final String TABLE_NAME = "recipe_ingredient";
        public static final String RECIPE_ID = "recipe_id";
        public static final String ORDER = "ordering";
        public static final String DESCRIPTION = "description";
        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        RECIPE_ID + " INTEGER," +
                        ORDER + " INTEGER NOT NULL," +
                        DESCRIPTION + " TEXT," +
                        "FOREIGN KEY(" + RECIPE_ID + ") REFERENCES " +
                        Recipes.TABLE_NAME + "(" + BaseColumns._ID + ")" +
                ");";
    }

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(Recipes.TABLE_CREATE);
        db.execSQL(Steps.TABLE_CREATE);
        db.execSQL(Ingredients.TABLE_CREATE);
    }
}
