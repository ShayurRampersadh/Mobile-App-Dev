package com.shayurrampersadh.smartpantrymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shayurrampersadh.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class PantryDao {

    private PantryDbHelper dbHelper;

    public PantryDao (Context context){
        dbHelper = new PantryDbHelper (context);
    }

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


}
