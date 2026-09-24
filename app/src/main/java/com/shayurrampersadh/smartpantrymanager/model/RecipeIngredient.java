package com.shayurrampersadh.smartpantrymanager.model;

public class RecipeIngredient {

    private int recipeIngredient;
    private String ingredientName;
    private double requiredQuantity;
    private String unit;

    public RecipeIngredient(int recipeIngredient, String ingredientName,
                            double requiredQuantity, String unit){
        this.recipeIngredient = recipeIngredient;
        this.ingredientName = ingredientName;
        this.unit = unit;
        this.requiredQuantity = requiredQuantity;
    }

    public int getRecipeIngredient(){
        return recipeIngredient;
    }

    public String getIngredientName(){
        return ingredientName;
    }

    public String getUnit(){
        return unit;
    }

    public double getRequiredQuantity(){
        return requiredQuantity;
    }
}
