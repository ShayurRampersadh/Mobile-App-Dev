package com.shayurrampersadh.smartpantrymanager.model;

/**
 * Represents one row in the recipes table — a recipe's name and its
 * prep steps. The actual required ingredients live in a separate
 * RecipeIngredient list, fetched via RecipeIngredientsDao.
 */
public class Recipe {

    private int id;
    private String name;
    private String prepSteps;

    public Recipe(int id, String name, String prepSteps){
        this.id = id;
        this.name = name;
        this.prepSteps = prepSteps;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getPrepSteps(){
        return prepSteps;
    }
}
