package com.example.finaiized.splatterbook.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.finaiized.splatterbook.persistence.RecipesContract.*;

import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class RecipeProvider extends ContentProvider {

    private RecipeOpenHelper openHelper;

    private static final int RECIPES = 1;
    private static final int RECIPES_ID = 2;
    private static final int RECIPES_STEPS = 3;
    private static final int RECIPES_INGREDIENTS = 4;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(RecipesContract.CONTENT_AUTHORITY, "recipes", RECIPES);
        uriMatcher.addURI(RecipesContract.CONTENT_AUTHORITY, "recipes/#", RECIPES_ID);
        uriMatcher.addURI(RecipesContract.CONTENT_AUTHORITY, "recipes/#/steps", RECIPES_STEPS);
        uriMatcher.addURI(RecipesContract.CONTENT_AUTHORITY, "recipes/#/ingredients", RECIPES_INGREDIENTS);
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
                queryBuilder.setTables(Table.Recipes.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) sortOrder = BaseColumns._ID + " ASC";
                break;
            case RECIPES_ID:
                queryBuilder.setTables(Table.Recipes.TABLE_NAME);
                queryBuilder.appendWhere(String.format("%s = %s", BaseColumns._ID, Recipes.getRecipeId(uri)));
                break;
            case RECIPES_STEPS:
                queryBuilder.setTables(Table.Steps.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Table.Steps.ORDER + " ASC";
                queryBuilder.appendWhere(String.format("%s = %s", Table.Steps.RECIPE_ID, Recipes.getRecipeId(uri)));
                break;
            case RECIPES_INGREDIENTS:
                queryBuilder.setTables(Table.Ingredients.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Table.Ingredients.ORDER + " ASC";
                queryBuilder.appendWhere(String.format("%s = %s", Table.Ingredients.RECIPE_ID, Recipes.getRecipeId(uri)));
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return queryBuilder.query(openHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case RECIPES: {
                long index = db.insertOrThrow(Table.Recipes.TABLE_NAME, null, values);
                return Recipes.buildRecipeUri(String.valueOf(index));
            }
            case RECIPES_STEPS: {
                long index = db.insertOrThrow(Table.Steps.TABLE_NAME, null, values);
                return Recipes.buildStepsUri(String.valueOf(index));
            }
            case RECIPES_INGREDIENTS: {
                long index = db.insertOrThrow(Table.Ingredients.TABLE_NAME, null, values);
                return Recipes.buildIngredientsUri(String.valueOf(index));
            }
            default:
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
        }
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
