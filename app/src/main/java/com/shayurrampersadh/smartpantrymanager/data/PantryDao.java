package com.shayurrampersadh.smartpantrymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shayurrampersadh.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the pantry_items table — the only class that
 * talks to that table directly. Converts database rows into PantryItem
 * objects (and back), so the rest of the app never deals with raw SQL
 * or Cursors.
 */
public class PantryDao {

    private PantryDbHelper dbHelper;

    public PantryDao (Context context){
        dbHelper = new PantryDbHelper (context);
    }

    /** Inserts a new pantry item and returns its auto-generated id. */
    public long insert(PantryItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COL_PANTRY_NAME, item.getName());
        values.put(PantryDbHelper.COL_QUANTITY, item.getQuantity());
        values.put(PantryDbHelper.COL_UNIT, item.getUnit());
        values.put(PantryDbHelper.COL_EXPIRY_DATE, item.getExpiryDate());

        long newId = db.insert(PantryDbHelper.TABLE_PANTRY, null, values);
        db.close();
        return newId;
    }

    /** Returns every pantry item currently stored, for the Pantry List screen. */
    public List<PantryItem> getAll(){
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // moveToFirst()/moveToNext() do-while is the standard way to read
        // every row out of a Cursor.
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_PANTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_PANTRY_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_UNIT));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_EXPIRY_DATE));

                items.add(new PantryItem(id, name, quantity, unit, expiry));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    /** Updates an existing pantry item by its id. Returns rows affected (0 or 1). */
    public int update(PantryItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COL_PANTRY_NAME, item.getName());
        values.put(PantryDbHelper.COL_QUANTITY, item.getQuantity());
        values.put(PantryDbHelper.COL_UNIT, item.getUnit());
        values.put(PantryDbHelper.COL_EXPIRY_DATE, item.getExpiryDate());

        int rowsAffected = db.update(
                PantryDbHelper.TABLE_PANTRY,
                values,
                PantryDbHelper.COL_PANTRY_ID + " = ?",
                new String[] {String.valueOf(item.getId())}
        );

        db.close();
        return rowsAffected;
    }

    /** Deletes a pantry item by id. Returns rows affected (0 or 1). */
    public int delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsAffected = db.delete(
                PantryDbHelper.TABLE_PANTRY,
                PantryDbHelper.COL_PANTRY_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsAffected;
    }

    /** Fetches one pantry item by id, or null if no row matches. Used when opening the edit form. */
    public PantryItem getById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        PantryItem item = null;

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_PANTRY,
                null,
                PantryDbHelper.COL_PANTRY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_PANTRY_NAME));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_UNIT));
            String expiry = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_EXPIRY_DATE));

            item = new PantryItem(id, name, quantity, unit, expiry);
        }

        cursor.close();
        db.close();
        return item;
    }

    /**
     * Fetches a pantry item by its EXACT name (case-sensitive), or null
     * if none exists. Used by AddEditIngredientActivity to decide whether
     * adding an item should merge into an existing row or insert a new one.
     */
    public PantryItem getByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        PantryItem item = null;

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_PANTRY,
                null,
                PantryDbHelper.COL_PANTRY_NAME + " = ?",
                new String[]{name},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_PANTRY_ID));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_UNIT));
            String expiry = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COL_EXPIRY_DATE));

            item = new PantryItem(id, name, quantity, unit, expiry);
        }

        cursor.close();
        db.close();
        return item;
    }


}
