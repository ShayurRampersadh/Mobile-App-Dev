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
public class RecipeMatcher {

    private RecipeIngredientsDao recipeIngredientsDao;

    public RecipeMatcher(RecipeIngredientsDao recipeIngredientsDao){
        this.recipeIngredientsDao = recipeIngredientsDao;
    }

    private Map <String, Double> buildPantryMap(List<PantryItem> pantryItems){
        Map <String, Double> pantryMap = new HashMap<>();

        for(PantryItem item : pantryItems){
            String normalizedName = IngredientNormalizer.normalize(item.getName());
            double existingQty = pantryMap.containsKey(normalizedName) ? pantryMap.get(normalizedName) : 0;
            pantryMap.put(normalizedName, existingQty + item.getQuantity());
        }
        return pantryMap;
    }

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

