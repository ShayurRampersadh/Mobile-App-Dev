package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.data.PantryDao;
import com.shayurrampersadh.smartpantrymanager.data.RecipeDao;
import com.shayurrampersadh.smartpantrymanager.data.RecipeIngredientsDao;

import com.shayurrampersadh.smartpantrymanager.matching.RecipeMatcher;
import com.shayurrampersadh.smartpantrymanager.model.MatchResult;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;
import com.shayurrampersadh.smartpantrymanager.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {


    private RecyclerView rvSuggested, rvAlmostThere;
    private TextView tvEmptyState, tvSuggestedHeader, tvAlmostThereHeader;

    private PantryDao pantryDao;
    private RecipeDao recipeDao;
    private RecipeIngredientsDao recipeIngredientsDao;
    private RecipeMatcher recipeMatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        pantryDao = new PantryDao(this);
        recipeDao = new RecipeDao(this);
        recipeIngredientsDao = new RecipeIngredientsDao(this);
        recipeMatcher = new RecipeMatcher(recipeIngredientsDao);

        rvSuggested = findViewById(R.id.rvSuggested);
        rvAlmostThere = findViewById(R.id.rvAlmostThere);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        tvSuggestedHeader = findViewById(R.id.tvSuggestedHeader);
        tvAlmostThereHeader = findViewById(R.id.tvAlmostThereHeader);

        rvSuggested.setLayoutManager(new LinearLayoutManager(this));
        rvAlmostThere.setLayoutManager(new LinearLayoutManager(this));

        loadSuggestions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestions();
    }

    private void loadSuggestions() {
        List<PantryItem> pantryItems = pantryDao.getAll();
        List<Recipe> allRecipes = recipeDao.getAll();
        List<MatchResult> allResults = recipeMatcher.matchAllRecipes(allRecipes, pantryItems);

        List<MatchResult> suggested = new ArrayList<>();
        List<MatchResult> almostThere = new ArrayList<>();

        for (MatchResult result : allResults) {
            if (result.getMissingCount() == 0) {
                suggested.add(result);
            } else if (result.getMissingCount() == 1) {
                almostThere.add(result);
            }
        }

        if (suggested.isEmpty() && almostThere.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            tvSuggestedHeader.setVisibility(View.GONE);
            tvAlmostThereHeader.setVisibility(View.GONE);
            rvSuggested.setVisibility(View.GONE);
            rvAlmostThere.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            tvSuggestedHeader.setVisibility(View.VISIBLE);
            tvAlmostThereHeader.setVisibility(View.VISIBLE);
            rvSuggested.setVisibility(View.VISIBLE);
            rvAlmostThere.setVisibility(View.VISIBLE);

            rvSuggested.setAdapter(new RecipeAdapter(suggested, this::openRecipeDetail));
            rvAlmostThere.setAdapter(new RecipeAdapter(almostThere, this::openRecipeDetail));
        }
    }

    private void openRecipeDetail(MatchResult matchResult) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, matchResult.getRecipe().getId());
        startActivity(intent);
    }

}

