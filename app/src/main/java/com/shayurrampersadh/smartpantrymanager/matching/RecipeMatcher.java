package com.shayurrampersadh.smartpantrymanager.matching;

import com.shayurrampersadh.smartpantrymanager.data.RecipeIngredientsDao;
import com.shayurrampersadh.smartpantrymanager.model.MatchResult;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;
import com.shayurrampersadh.smartpantrymanager.model.Recipe;
import com.shayurrampersadh.smartpantrymanager.model.RecipeIngredient;
import com.shayurrampersadh.smartpantrymanager.util.IngredientNormalizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the assignment's core business logic: the strict-matching
 * rule. Deliberately UI-independent — takes plain lists and returns
 * plain MatchResult objects, with no dependency on any Activity or
 * screen, so it can be reasoned about (and was tested) on its own.
 */
public class RecipeMatcher {

    private RecipeIngredientsDao recipeIngredientsDao;

    public RecipeMatcher(RecipeIngredientsDao recipeIngredientsDao){
        this.recipeIngredientsDao = recipeIngredientsDao;
    }

    /**
     * Converts the pantry's item list into a fast lookup map, keyed by
     * NORMALISED ingredient name, with quantities of same-named items
     * summed together. Built once per matching run rather than re-scanning
     * the pantry list for every recipe.
     */
    private Map <String, Double> buildPantryMap(List<PantryItem> pantryItems){
        Map <String, Double> pantryMap = new HashMap<>();

        for(PantryItem item : pantryItems){
            String normalizedName = IngredientNormalizer.normalize(item.getName());
            double existingQty = pantryMap.containsKey(normalizedName) ? pantryMap.get(normalizedName) : 0;
            pantryMap.put(normalizedName, existingQty + item.getQuantity());
        }
        return pantryMap;
    }

    /**
     * The strict-matching rule for ONE recipe: every required ingredient
     * must be present in the pantry map in at least the required quantity,
     * or it counts as missing. Returns a MatchResult wrapping the recipe,
     * how many ingredients are missing, and which ones.
     */
    public MatchResult matchRecipe(Recipe recipe, Map<String, Double> pantryMap){
        List<RecipeIngredient> requiredIngredients = recipeIngredientsDao.getIngredientForRecipe(recipe.getId());
        List<String> missingIngredients = new ArrayList<>();

        for (RecipeIngredient required : requiredIngredients) {
            String normalizedName = IngredientNormalizer.normalize(required.getIngredientName());
            double availableQty = pantryMap.containsKey(normalizedName) ? pantryMap.get(normalizedName) : 0;

            if (availableQty < required.getRequiredQuantity()) {
                missingIngredients.add(required.getIngredientName());
            }
        }

        return new MatchResult(recipe, missingIngredients.size(), missingIngredients);
    }

    /**
     * Runs matchRecipe() across every recipe against one pantry snapshot.
     * The pantry map is built ONCE here and reused for every recipe,
     * rather than rebuilt inside the loop.
     */
    public List<MatchResult> matchAllRecipes(List<Recipe> allRecipes, List<PantryItem> pantryItems) {


        Map<String, Double> pantryMap = buildPantryMap(pantryItems);
        List<MatchResult> results = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            MatchResult result = matchRecipe(recipe, pantryMap);
            results.add(result);
        }
        return results;
    }
}

