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

        seedRecipe(" Cheese Toast",
                "1. Butter one side of each bread slice. 2. Place cheese between slices. 3. Grill in a pan on medium heat until golden and cheese melts.",
                new RecipeIngredient(0, "bread", 2, "pcs"),
                new RecipeIngredient(0, "cheese", 50, "g"),
                new RecipeIngredient(0, "butter", 10, "g")
        );

        seedRecipe("Tomato Pasta",
                "1. Boil pasta until al dente. 2. Saute garlic in oil. 3. Add chopped tomato and salt, simmer 10 minutes. 4. Throw in pasta in sauce and serve.",
                new RecipeIngredient(0, "pasta", 200, "g"),
                new RecipeIngredient(0, "tomato", 3, "pcs"),
                new RecipeIngredient(0, "garlic", 2, "pcs"),
                new RecipeIngredient(0, "oil", 15, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Pancakes",
                "1. Mix flour, sugar, and a pinch of salt. 2. Whisk in milk and eggs until smooth. 3. Cook spoonfuls of batter in a buttered pan until bubbles form, while flipping.",
                new RecipeIngredient(0, "flour", 200, "g"),
                new RecipeIngredient(0, "milk", 250, "ml"),
                new RecipeIngredient(0, "eggs", 2, "pcs"),
                new RecipeIngredient(0, "sugar", 30, "g"),
                new RecipeIngredient(0, "butter", 15, "g")
        );

        seedRecipe("Fried Rice",
                "1. Heat oil in a pan or wok. 2. Scramble eggs, push to the side. 3. Add cooked rice and chopped onion, stir-fry. 4. Mix in eggs and season with salt.",
                new RecipeIngredient(0, "rice", 300, "g"),
                new RecipeIngredient(0, "eggs", 2, "pcs"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "oil", 15, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Banana Oatmeal",
                "1. Bring milk to a simmer. 2. Stir in oats, cook 5 minutes. 3. Top with sliced banana and a drizzle of honey.",
                new RecipeIngredient(0, "oats", 80, "g"),
                new RecipeIngredient(0, "milk", 200, "ml"),
                new RecipeIngredient(0, "banana", 1, "pcs"),
                new RecipeIngredient(0, "honey", 15, "ml")
        );

        seedRecipe("Garlic Bread",
                "1. Mix softened butter with minced garlic. 2. Spread on sliced bread. 3. Grill or bake until golden and crisp.",
                new RecipeIngredient(0, "bread", 4, "pcs"),
                new RecipeIngredient(0, "butter", 40, "g"),
                new RecipeIngredient(0, "garlic", 3, "pcs")
        );

        seedRecipe("Cheese Omelette",
                "1. Whisk eggs with a pinch of salt and pepper. 2. Pour into a hot buttered pan. 3. Sprinkle cheese on one half, fold, and cook until set.",
                new RecipeIngredient(0, "eggs", 3, "pcs"),
                new RecipeIngredient(0, "cheese", 40, "g"),
                new RecipeIngredient(0, "salt", 1, "g"),
                new RecipeIngredient(0, "butter", 10, "g")
        );

        seedRecipe("Chicken Stir Fry",
                "1. Heat oil in a wok. 2. Stir-fry sliced chicken until browned. 3. Add chopped onion and garlic, cook until soft. 4. Season with salt and serve.",
                new RecipeIngredient(0, "chicken", 300, "g"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "garlic", 2, "pcs"),
                new RecipeIngredient(0, "oil", 20, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Rice and Beans",
                "1. Sauté chopped onion and garlic in oil. 2. Add cooked beans and cooked rice, stir to combine. 3. Season with salt and heat through.",
                new RecipeIngredient(0, "rice", 250, "g"),
                new RecipeIngredient(0, "beans", 200, "g"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "garlic", 1, "pcs"),
                new RecipeIngredient(0, "oil", 10, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Peanut Butter Toast",
                "1. Toast the bread. 2. Spread peanut butter evenly. 3. Optionally drizzle with honey.",
                new RecipeIngredient(0, "bread", 2, "pcs"),
                new RecipeIngredient(0, "peanut butter", 30, "g"),
                new RecipeIngredient(0, "honey", 10, "ml")
        );

        seedRecipe("Vegetable Soup",
                "1. Sauté chopped onion and garlic in oil. 2. Add chopped tomato, carrot, and water, simmer 20 minutes. 3. Season with salt and serve hot.",
                new RecipeIngredient(0, "tomato", 2, "pcs"),
                new RecipeIngredient(0, "carrot", 2, "pcs"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "garlic", 1, "pcs"),
                new RecipeIngredient(0, "oil", 10, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Cheesy Scrambled Eggs",
                "1. Whisk eggs with a pinch of salt. 2. Cook on low heat in a buttered pan, stirring gently. 3. Fold in grated cheese just before fully set.",
                new RecipeIngredient(0, "eggs", 3, "pcs"),
                new RecipeIngredient(0, "cheese", 30, "g"),
                new RecipeIngredient(0, "butter", 10, "g"),
                new RecipeIngredient(0, "salt", 1, "g")
        );

        seedRecipe("Carrot and Onion Rice",
                "1. Sauté chopped onion and grated carrot in oil until soft. 2. Add cooked rice and stir to combine. 3. Season with salt.",
                new RecipeIngredient(0, "rice", 250, "g"),
                new RecipeIngredient(0, "carrot", 2, "pcs"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "oil", 10, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Chicken Curry",
                "1. Sauté chopped onion and garlic in oil until soft. 2. Add chicken pieces and brown lightly. 3. Stir in chopped tomato and curry powder, add a splash of water, and simmer until chicken is cooked through. 4. Season with salt.",
                new RecipeIngredient(0, "chicken", 300, "g"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "garlic", 2, "pcs"),
                new RecipeIngredient(0, "tomato", 2, "pcs"),
                new RecipeIngredient(0, "curry powder", 10, "g"),
                new RecipeIngredient(0, "oil", 15, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Chicken Biryani",
                "1. Sauté chopped onion and garlic in oil until golden. 2. Add chicken pieces and curry powder, let it cook. 3. Stir in rice and enough water to cook it, cover and simmer until rice is tender. 4. Season with salt.",
                new RecipeIngredient(0, "chicken", 300, "g"),
                new RecipeIngredient(0, "rice", 250, "g"),
                new RecipeIngredient(0, "onion", 1, "pcs"),
                new RecipeIngredient(0, "garlic", 2, "pcs"),
                new RecipeIngredient(0, "curry powder", 10, "g"),
                new RecipeIngredient(0, "oil", 15, "ml"),
                new RecipeIngredient(0, "salt", 2, "g")
        );

        seedRecipe("Garlic Butter Chicken",
                "1. Melt butter in a pan with minced garlic. 2. Add chicken pieces and cook through, stirring occasionally. 3. Season with salt and serve.",
                new RecipeIngredient(0, "chicken", 300, "g"),
                new RecipeIngredient(0, "garlic", 3, "pcs"),
                new RecipeIngredient(0, "butter", 20, "g"),
                new RecipeIngredient(0, "salt", 2, "g")
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