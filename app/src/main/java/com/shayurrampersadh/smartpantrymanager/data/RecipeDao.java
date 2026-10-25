package com.shayurrampersadh.smartpantrymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shayurrampersadh.smartpantrymanager.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the recipes table. Recipes are read-only from
 * the app's perspective — insert() is only ever called during seeding,
 * never through any screen.
 */
public class RecipeDao {

    private PantryDbHelper dbHelper;

    public RecipeDao(Context context){
        dbHelper = new PantryDbHelper(context);
    }

    /** Inserts one recipe (name + prep steps) and returns its auto-generated id. Used only by DbSeeder. */
    public long insert(Recipe recipe){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COL_RECIPE_NAME, recipe.getName());
        values.put(PantryDbHelper.COL_RECIPE_STEPS, recipe.getPrepSteps());

        long newId = db.insert(PantryDbHelper.TABLE_RECIPES, null, values);
        db.close();
        return newId;
    }

    /** Returns every recipe — used by RecipeMatcher to run matching against the whole set. */
    public List<Recipe> getAll() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_RECIPES,
                null, null, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RECIPE_NAME));
                String steps = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RECIPE_STEPS));

                recipes.add(new Recipe(id, name, steps));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recipes;
    }

    /**
     * Returns how many rows are in the recipes table. Used by DbSeeder as
     * an "already seeded?" check — a count greater than 0 means seeding
     * has already happened, so it should not run again.
     */
    public int getCount(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(PantryDbHelper.TABLE_RECIPES, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /** Fetches one recipe by id, or null if none matches. Used by RecipeDetailActivity. */
    public Recipe getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Recipe recipe = null;

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_RECIPES,
                null,
                PantryDbHelper.COL_RECIPE_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RECIPE_NAME));
            String steps = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RECIPE_STEPS));

            recipe = new Recipe(id, name, steps);
        }

        cursor.close();
        db.close();
        return recipe;
    }
}
