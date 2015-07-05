package com.example.finaiized.splatterbook.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

public final class RecipesContract {
    public static final String CONTENT_AUTHORITY = "com.example.finaiized.splatterbook.provider";
    private static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_RECIPES = "recipes";
    private static final String PATH_STEPS = "steps";
    private static final String PATH_INGREDIENTS = "ingredients";

    public static final class Recipes {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH_RECIPES);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.finaiized.splatterbook.provider.recipes";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.finaiized.splatterbook.provider.recipes";

        public static final String ID = BaseColumns._ID;
        public static final String TITLE = Table.Recipes.TITLE;
        public static final String DESCRIPTION = Table.Recipes.DESCRIPTION;
        public static final String TIMES_MADE = Table.Recipes.TIMES_MADE;
        public static final String PREP_TIME = Table.Recipes.PREP_TIME;
        public static final String COOK_TIME = Table.Recipes.COOK_TIME;

        public static String getRecipeId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildRecipeUri(String recipeId) {
            return CONTENT_URI.buildUpon().appendPath(recipeId).build();
        }

        public static Uri buildStepsUri(String recipeId) {
            return CONTENT_URI.buildUpon().appendPath(recipeId).appendPath(PATH_STEPS).build();
        }

        public static Uri buildIngredientsUri(String recipeId) {
            return CONTENT_URI.buildUpon().appendPath(recipeId).appendPath(PATH_INGREDIENTS).build();
        }
    }

    public static final class Steps {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.finaiized.splatterbook.provider.steps";

        public static final String RECIPE_ID = Table.Steps.RECIPE_ID;
        public static final String ORDER = Table.Steps.ORDER;
        public static final String DESCRIPTION = Table.Steps.DESCRIPTION;
    }

    public static final class Ingredients {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.finaiized.splatterbook.provider.ingredients";

        public static final String RECIPE_ID = Table.Ingredients.RECIPE_ID;
        public static final String ORDER = Table.Ingredients.ORDER;
        public static final String DESCRIPTION = Table.Ingredients.DESCRIPTION;
    }
}
