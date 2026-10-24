package com.shayurrampersadh.smartpantrymanager.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.data.RecipeDao;
import com.shayurrampersadh.smartpantrymanager.data.RecipeIngredientsDao;
import com.shayurrampersadh.smartpantrymanager.model.Recipe;
import com.shayurrampersadh.smartpantrymanager.model.RecipeIngredient;
import com.shayurrampersadh.smartpantrymanager.util.IngredientNormalizer;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);

        RecipeDao recipeDao = new RecipeDao(this);
        RecipeIngredientsDao recipeIngredientsDao = new RecipeIngredientsDao(this);

        Recipe recipe = recipeDao.getById(recipeId);

        if (recipe == null) {
            finish();
            return;
        }

        List<RecipeIngredient> ingredients = recipeIngredientsDao.getIngredientForRecipe(recipeId);

        TextView tvName = findViewById(R.id.tvDetailRecipeName);
        TextView tvIngredients = findViewById(R.id.tvIngredientsList);
        TextView tvSteps = findViewById(R.id.tvStepsText);

        tvName.setText(recipe.getName());

        StringBuilder ingredientsText = new StringBuilder();
        for (RecipeIngredient ingredient : ingredients) {
            ingredientsText.append("• ")
                    .append(IngredientNormalizer.formatQuantity(ingredient.getRequiredQuantity()))
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append(" ")
                    .append(ingredient.getIngredientName())
                    .append("\n");
        }
        tvIngredients.setText(ingredientsText.toString());

        tvSteps.setText(recipe.getPrepSteps());
    }

}