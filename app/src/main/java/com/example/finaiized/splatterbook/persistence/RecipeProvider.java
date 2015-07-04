package com.example.finaiized.splatterbook.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class RecipeProvider extends ContentProvider {

    private RecipeOpenHelper openHelper;

    public static final String AUTHORITY = "com.example.finaiized.splatterbook.provider";
    private static final int RECIPES = 1;
    private static final int RECIPES_ID = 2;
    public static final int RECIPES_STEPS = 3;
    public static final int RECIPES_INGREDIENTS = 4;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "recipes", RECIPES);
        uriMatcher.addURI(AUTHORITY, "recipes/#", RECIPES_ID);
        uriMatcher.addURI(AUTHORITY, "recipes/#/steps", RECIPES_STEPS);
        uriMatcher.addURI(AUTHORITY, "recipes/#/ingredients", RECIPES_INGREDIENTS);
    }

    @Override
    public boolean onCreate() {
        openHelper = new RecipeOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case RECIPES:
                queryBuilder.setTables(RecipeOpenHelper.RECIPE_TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) sortOrder = BaseColumns._ID + " ASC";
                break;
            case RECIPES_ID:
                queryBuilder.setTables(RecipeOpenHelper.RECIPE_TABLE_NAME);
                queryBuilder.appendWhere(String.format("%s = %s", BaseColumns._ID, uri.getLastPathSegment()));
                break;
            case RECIPES_STEPS:
                queryBuilder.setTables(RecipeOpenHelper.RECIPE_STEP_TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) sortOrder = RecipeOpenHelper.KEY_RECIPE_STEP_ORDER + " ASC";
                queryBuilder.appendWhere(String.format("%s = %s", RecipeOpenHelper.KEY_RECIPE_STEP_ID, uri.getPathSegments().get(1)));
                break;
            case RECIPES_INGREDIENTS:
                queryBuilder.setTables(RecipeOpenHelper.RECIPE_INGREDIENT_TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) sortOrder = RecipeOpenHelper.KEY_RECIPE_INGREDIENT_ORDER + " ASC";
                queryBuilder.appendWhere(String.format("%s = %s", RecipeOpenHelper.KEY_RECIPE_INGREDIENT_ID, uri.getPathSegments().get(1)));
                break;
        }

        return queryBuilder.query(openHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
