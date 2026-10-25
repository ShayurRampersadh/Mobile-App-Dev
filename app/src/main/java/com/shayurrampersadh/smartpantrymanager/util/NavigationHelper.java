package com.shayurrampersadh.smartpantrymanager.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.ui.PantryListActivity;
import com.shayurrampersadh.smartpantrymanager.ui.SettingsActivity;
import com.shayurrampersadh.smartpantrymanager.ui.SuggestedRecipesActivity;

/**
 * Shared bottom-navigation logic used by PantryListActivity,
 * SuggestedRecipesActivity, and SettingsActivity — written once here
 * instead of duplicated three times. Each Activity just calls
 * setupNavBar(this) from onCreate().
 */
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
        // Reuses an existing instance of the target screen instead of stacking
        // a new one every time, so repeatedly switching tabs doesn't bloat the
        // back stack (and pressing back doesn't cycle through every tab visited).
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    /** Colors the button matching the Activity you're currently on; resets the other two. */
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