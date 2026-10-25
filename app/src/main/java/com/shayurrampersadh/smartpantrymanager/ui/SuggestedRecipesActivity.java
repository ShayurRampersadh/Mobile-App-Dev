package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.shayurrampersadh.smartpantrymanager.util.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private TextView tvEmptyState;
    private Button btnTabSuggested, btnTabAlmostThere;

    private PantryDao pantryDao;
    private RecipeDao recipeDao;
    private RecipeIngredientsDao recipeIngredientsDao;
    private RecipeMatcher recipeMatcher;

    private List<MatchResult> suggestedResults = new ArrayList<>();
    private List<MatchResult> almostThereResults = new ArrayList<>();
    private boolean showingSuggested = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        pantryDao = new PantryDao(this);
        recipeDao = new RecipeDao(this);
        recipeIngredientsDao = new RecipeIngredientsDao(this);
        recipeMatcher = new RecipeMatcher(recipeIngredientsDao);

        rvRecipes = findViewById(R.id.rvRecipes);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        btnTabSuggested = findViewById(R.id.btnTabSuggested);
        btnTabAlmostThere = findViewById(R.id.btnTabAlmostThere);

        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        btnTabSuggested.setOnClickListener(v -> {
            showingSuggested = true;
            displayCurrentTab();
        });

        btnTabAlmostThere.setOnClickListener(v -> {
            showingSuggested = false;
            displayCurrentTab();
        });

        NavigationHelper.setupNavBar(this);

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

        suggestedResults = new ArrayList<>();
        almostThereResults = new ArrayList<>();

        for (MatchResult result : allResults) {
            if (result.getMissingCount() == 0) {
                suggestedResults.add(result);
            } else if (result.getMissingCount() == 1) {
                almostThereResults.add(result);
            }
        }

        displayCurrentTab();
    }

    private void displayCurrentTab() {
        updateTabHighlight();

        List<MatchResult> currentList = showingSuggested ? suggestedResults : almostThereResults;

        if (currentList.isEmpty()) {
            rvRecipes.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
            tvEmptyState.setText(showingSuggested
                    ? "No recipes match your pantry yet - add more ingredients"
                    : "No recipes are just one ingredient away yet");
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvRecipes.setVisibility(View.VISIBLE);
            rvRecipes.setAdapter(new RecipeAdapter(currentList, this::openRecipeDetail));
        }
    }

    private void updateTabHighlight() {
        int activeColor = ContextCompat.getColor(this, R.color.heading_red);
        int inactiveColor = ContextCompat.getColor(this, R.color.button_red_light);

        if (showingSuggested) {
            btnTabSuggested.setBackgroundColor(activeColor);
            btnTabSuggested.setTextColor(Color.WHITE);
            btnTabAlmostThere.setBackgroundColor(inactiveColor);
            btnTabAlmostThere.setTextColor(Color.BLACK);
        } else {
            btnTabAlmostThere.setBackgroundColor(activeColor);
            btnTabAlmostThere.setTextColor(Color.WHITE);
            btnTabSuggested.setBackgroundColor(inactiveColor);
            btnTabSuggested.setTextColor(Color.BLACK);
        }
    }

    private void openRecipeDetail(MatchResult matchResult) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, matchResult.getRecipe().getId());
        startActivity(intent);
    }

}