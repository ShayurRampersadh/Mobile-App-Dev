package com.shayurrampersadh.smartpantrymanager.model;

/**
 * Represents one row in the pantry_items table — a single ingredient
 * the user currently has, with how much of it and when it expires.
 */
public class PantryItem {

    private int id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    public PantryItem(int id, String name, double quantity, String unit, String expiryDate){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getQuantity(){
        return quantity;
    }

    public String getUnit(){
        return unit;
    }

    public String getExpiryDate(){
        return expiryDate;
    }

    // No setId() — the id is assigned once by SQLite's AUTOINCREMENT and
    // should never be reassigned on an existing object.
    public void setName(String name){
        this.name = name;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setExpiryDate(String expiryDate){
        this.expiryDate = expiryDate;
    }

}
