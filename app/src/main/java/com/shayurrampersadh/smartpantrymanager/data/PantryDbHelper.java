package com.shayurrampersadh.smartpantrymanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages the SQLite database file and its schema. Creates all three
 * tables (pantry_items, recipes, recipe_ingredients) the first time the
 * app runs. Column/table name constants live here so the rest of the
 * app never hardcodes raw strings in SQL — a typo in a constant name
 * is a compile error, a typo in a raw string is a silent runtime bug.
 */

public class PantryDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "smartpantry.db";
    private static final int DATABASE_VERSION = 1;


    // pantry_items table
    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COL_PANTRY_ID = "id";
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY_DATE = "expiry_date";

    // recipes table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_STEPS = "prep_steps";

    // recipe_ingredients table, links back to recipes via COL_RI_RECIPE_ID
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COL_RI_RECIPE_ID = "recipe_id";
    public static final String COL_RI_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_RI_REQUIRED_QUANTITY = "required_quantity";
    public static final String COL_RI_UNIT = "unit";

    public PantryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called automatically by Android the first time the database file is created.
    @Override
    public void onCreate(SQLiteDatabase db){
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANTRY_NAME + " TEXT NOT NULL, " +
                COL_QUANTITY + " REAL NOT NULL, " +
                COL_UNIT + " TEXT, " +
                COL_EXPIRY_DATE + " TEXT " +
                ")";

        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT NOT NULL, "+
                COL_RECIPE_STEPS + " TEXT" +
                ")";


        // No PRIMARY KEY here on purpose — one recipe has MANY ingredient rows,
        // all sharing the same recipe_id, so recipe_id can't be a unique key.
        String createRecipeIngredientsTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " ("+
                COL_RI_RECIPE_ID + " INTEGER NOT NULL, "+
                COL_RI_INGREDIENT_NAME + " TEXT NOT NULL, " +
                COL_RI_REQUIRED_QUANTITY + " REAL NOT NULL, " +
                COL_RI_UNIT + " TEXT, "+
                "FOREIGN KEY( " + COL_RI_RECIPE_ID + " ) REFERENCES " + TABLE_RECIPES + "(" + COL_RECIPE_ID + ")" +
                ")";

        db.execSQL(createPantryTable);
        db.execSQL(createRecipesTable);
        db.execSQL(createRecipeIngredientsTable);
    }

    // This only runs if DATABASE_VERSION is increased in a future update.
    // Simplest possible strategy: wipe and recreate (fine for coursework,
    // not for production, since it loses existing user data).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);

        onCreate(db);
    }

}
