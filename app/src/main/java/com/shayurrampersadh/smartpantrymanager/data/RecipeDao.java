package com.shayurrampersadh.smartpantrymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shayurrampersadh.smartpantrymanager.model.Recipe;

import java.util.ArrayList;
import java.util.List;
public class RecipeDao {

    private PantryDbHelper dbHelper;

    public RecipeDao(Context context){
        dbHelper = new PantryDbHelper(context);
    }

    public long insert(Recipe recipe){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COL_RECIPE_NAME, recipe.getName());
        values.put(PantryDbHelper.COL_RECIPE_STEPS, recipe.getPrepSteps());

        long newId = db.insert(PantryDbHelper.TABLE_RECIPES, null, values);
        db.close();
        return newId;
    }

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

    public int getCount(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(PantryDbHelper.TABLE_RECIPES, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
