package com.shayurrampersadh.smartpantrymanager.model;

import java.util.List;

/**
 * This is the output of running the strict-matching rule against one recipe.
 * Not a database row, built by RecipeMatcher at runtime. missingCount
 * of 0 means the recipe is fully matched (Suggested); 1 means it
 * belongs in the Almost There list.
 */
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