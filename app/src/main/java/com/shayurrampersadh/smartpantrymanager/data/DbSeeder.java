package com.shayurrampersadh.smartpantrymanager.data;

import android.content.Context;

import com.shayurrampersadh.smartpantrymanager.model.Recipe;
import com.shayurrampersadh.smartpantrymanager.model.RecipeIngredient;

public class DbSeeder {

    private RecipeDao recipeDao;
    private RecipeIngredientsDao recipeIngredientsDao;

    public DbSeeder(Context context) {
        recipeDao = new RecipeDao(context);
        recipeIngredientsDao = new RecipeIngredientsDao(context);
    }

    public void seedIfEmpty() {

        if (recipeDao.getCount() > 0) {
            return;
        }

        seedRecipe("Scrambled Eggs on Toast",
                "1. Whisk eggs. 2. Melt butter in a pan. 3. Cook eggs on low heat, stirring gently. 4. Toast bread. 5. Serve eggs on toast.",
                new RecipeIngredient(0, "eggs", 2, "pcs"),
                new RecipeIngredient(0, "bread", 2, "pcs"),
                new RecipeIngredient(0, "butter", 10, "g")
        );

    }

    private void seedRecipe(String name, String steps, RecipeIngredient... ingredients) {
        Recipe recipe = new Recipe(0, name, steps);
        long recipeId = recipeDao.insert(recipe);

        for (RecipeIngredient ingredient : ingredients) {
            RecipeIngredient toInsert = new RecipeIngredient(
                    (int) recipeId,
                    ingredient.getIngredientName(),
                    ingredient.getRequiredQuantity(),
                    ingredient.getUnit()
            );
            recipeIngredientsDao.insert(toInsert);
        }
    }

}