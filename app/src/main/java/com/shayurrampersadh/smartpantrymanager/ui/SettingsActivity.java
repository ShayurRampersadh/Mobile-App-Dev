package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.util.NavigationHelper;

/**
 * Settings screen. Holds one toggle (expiring-soon alerts), persisted
 * with SharedPreferences rather than SQLite, since it's a single simple
 * flag rather than structured, queryable data. PantryAdapter reads the
 * same PREFS_NAME/KEY_EXPIRY_ALERT keys to decide whether to show the
 * "Expiring soon" label on pantry items.
 */
public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SmartPantryPrefs";
    public static final String KEY_EXPIRY_ALERT = "expiry_alert_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Switch switchExpiryAlert = findViewById(R.id.switchExpiryAlert);

        // Defaults to true (alerts on) the very first time Settings is opened.
        boolean currentValue = prefs.getBoolean(KEY_EXPIRY_ALERT, true);
        switchExpiryAlert.setChecked(currentValue);

        switchExpiryAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_EXPIRY_ALERT, isChecked).apply();
        });

        NavigationHelper.setupNavBar(this);

    }

}