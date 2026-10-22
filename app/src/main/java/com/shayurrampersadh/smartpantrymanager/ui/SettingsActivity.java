package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.util.NavigationHelper;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SmartPantryPrefs";
    public static final String KEY_EXPIRY_ALERT = "expiry_alert_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Switch switchExpiryAlert = findViewById(R.id.switchExpiryAlert);

        boolean currentValue = prefs.getBoolean(KEY_EXPIRY_ALERT, true);
        switchExpiryAlert.setChecked(currentValue);

        switchExpiryAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_EXPIRY_ALERT, isChecked).apply();
        });

        NavigationHelper.setupNavBar(this);

    }

}