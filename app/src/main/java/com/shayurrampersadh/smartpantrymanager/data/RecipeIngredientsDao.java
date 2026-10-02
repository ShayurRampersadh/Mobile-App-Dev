package com.shayurrampersadh.smartpantrymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shayurrampersadh.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
public class RecipeIngredientsDao {

    private PantryDbHelper dbHelper;

    public RecipeIngredientsDao(Context context){
        dbHelper = new PantryDbHelper(context);
    }

    public long insert(RecipeIngredient ingredient){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PantryDbHelper.COL_RI_RECIPE_ID, ingredient.getRecipeId());
        values.put(PantryDbHelper.COL_RI_INGREDIENT_NAME, ingredient.getIngredientName());
        values.put(PantryDbHelper.COL_RI_REQUIRED_QUANTITY, ingredient.getRequiredQuantity());
        values.put(PantryDbHelper.COL_RI_UNIT, ingredient.getUnit());

        long newId = db.insert(PantryDbHelper.TABLE_RECIPE_INGREDIENTS, null, values);
        db.close();
        return newId;
    }

    public List <RecipeIngredient> getIngredientForRecipe(int recipeId){
        List<RecipeIngredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_RECIPE_INGREDIENTS,
                null,
                PantryDbHelper.COL_RI_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RI_INGREDIENT_NAME));
                double qty = cursor.getDouble(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RI_REQUIRED_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_RI_UNIT));

                ingredients.add(new RecipeIngredient(recipeId, name, qty, unit));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ingredients;
    }

}
