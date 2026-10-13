package com.shayurrampersadh.smartpantrymanager.model;

import java.util.List;

public class MatchResult {

    private Recipe recipe;
    private int missingCount;
    private List<String> missingIngredients;

    public MatchResult(Recipe recipe, int missingCount, List<String> missingIngredients){
        this.recipe = recipe;
        this.missingCount = missingCount;
        this.missingIngredients = missingIngredients;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public int getMissingCount(){
        return missingCount;
    }

    public List<String> getMissingIngredients(){
        return missingIngredients;
    }

}