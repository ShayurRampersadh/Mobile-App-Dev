package com.shayurrampersadh.smartpantrymanager.model;

import java.util.List;

public class MatchResult {

    private Recipe recipe;
    private int missingCount;
    private List<String> missingIngredient;

    public MatchResult(Recipe recipe, int missingCount, List missingIngredient){
        this.recipe = recipe;
        this.missingCount = missingCount;
        this.missingIngredient = missingIngredient;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public int getMissingCount(){
        return missingCount;
    }

    public List getMissingIngredient(){
        return missingIngredient;
    }


}
