package com.shayurrampersadh.smartpantrymanager.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.ui.PantryListActivity;
import com.shayurrampersadh.smartpantrymanager.ui.SettingsActivity;
import com.shayurrampersadh.smartpantrymanager.ui.SuggestedRecipesActivity;

public class NavigationHelper {

    public static void setupNavBar(Activity activity) {
        Button navPantry = activity.findViewById(R.id.navPantry);
        Button navRecipes = activity.findViewById(R.id.navRecipes);
        Button navSettings = activity.findViewById(R.id.navSettings);

        navPantry.setOnClickListener(v -> navigateTo(activity, PantryListActivity.class));
        navRecipes.setOnClickListener(v -> navigateTo(activity, SuggestedRecipesActivity.class));
        navSettings.setOnClickListener(v -> navigateTo(activity, SettingsActivity.class));

        highlightCurrentTab(activity, navPantry, navRecipes, navSettings);
    }

    private static void navigateTo(Activity activity, Class<?> targetActivity) {
        if (activity.getClass().equals(targetActivity)) {
            return;
        }

        Intent intent = new Intent(activity, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    private static void highlightCurrentTab(Activity activity, Button navPantry, Button navRecipes, Button navSettings) {
        navPantry.setTextColor(Color.BLACK);
        navRecipes.setTextColor(Color.BLACK);
        navSettings.setTextColor(Color.BLACK);

        int dimmedBlue = Color.parseColor("#1565C0");

        if (activity instanceof PantryListActivity) {
            navPantry.setTextColor(dimmedBlue);
        } else if (activity instanceof SuggestedRecipesActivity) {
            navRecipes.setTextColor(dimmedBlue);
        } else if (activity instanceof SettingsActivity) {
            navSettings.setTextColor(dimmedBlue);
        }
    }

}