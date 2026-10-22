package com.shayurrampersadh.smartpantrymanager.util;

import android.app.Activity;
import android.content.Intent;
import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.ui.PantryListActivity;
import com.shayurrampersadh.smartpantrymanager.ui.SettingsActivity;
import com.shayurrampersadh.smartpantrymanager.ui.SuggestedRecipesActivity;

public class NavigationHelper {

    public static void setupNavBar(Activity activity) {
        activity.findViewById(R.id.navPantry).setOnClickListener(v ->
                navigateTo(activity, PantryListActivity.class));

        activity.findViewById(R.id.navRecipes).setOnClickListener(v ->
                navigateTo(activity, SuggestedRecipesActivity.class));

        activity.findViewById(R.id.navSettings).setOnClickListener(v ->
                navigateTo(activity, SettingsActivity.class));
    }

    private static void navigateTo(Activity activity, Class<?> targetActivity) {
        if (activity.getClass().equals(targetActivity)) {
            return; // already on this screen, do nothing
        }

        Intent intent = new Intent(activity, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

}