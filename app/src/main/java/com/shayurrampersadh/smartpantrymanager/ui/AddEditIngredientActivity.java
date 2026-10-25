package com.shayurrampersadh.smartpantrymanager.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.data.PantryDao;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;
import com.shayurrampersadh.smartpantrymanager.util.IngredientNormalizer;

import java.util.Calendar;
import java.util.Locale;

/**
 * Single form screen handling both add and edit. Mode is decided by
 * whether EXTRA_ITEM_ID was passed in via Intent. Expiry is entered
 * through a DatePickerDialog rather than typed, so an invalid date
 * is impossible rather than merely validated against.
 */
public class AddEditIngredientActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM_ID = "extra_item_id";

    private EditText etName, etQuantity, etUnit, etExpiry;

    private PantryDao pantryDao;
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        pantryDao = new PantryDao(this);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        etExpiry.setFocusable(false);
        etExpiry.setOnClickListener(v -> showDatePicker());

        itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);

        if (itemId != -1) {
            loadExistingItem();
        }

        btnSave.setOnClickListener(view -> saveItem());
        btnCancel.setOnClickListener(view -> finish());
    }

    /** Opens the system date picker, pre-filled with the existing date if one is set. */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        String existing = etExpiry.getText().toString().trim();

        if (!existing.isEmpty()) {
            try {
                String[] parts = existing.split("-");
                calendar.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]));
            } catch (Exception e) {
                // fall back to today if the stored value can't be parsed
            }
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                            selectedYear, selectedMonth + 1, selectedDay);
                    etExpiry.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    /** Edit mode: pre-fills the form with the item's current values. */
    private void loadExistingItem() {
        PantryItem item = pantryDao.getById(itemId);

        if (item != null) {
            etName.setText(item.getName());
            etQuantity.setText(String.valueOf(item.getQuantity()));
            etUnit.setText(item.getUnit());
            etExpiry.setText(item.getExpiryDate());
        }
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        // Normalised to Title Case so "milk" / "MILK" / "Milk" are treated
        // as the same pantry item regardless of how the user typed it.
        name = IngredientNormalizer.toTitleCase(name);
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name is required");
            return;
        }

        if (quantityStr.isEmpty()) {
            etQuantity.setError("Quantity is required");
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid number");
            return;
        }

        if (expiry.isEmpty()) {
            expiry = null;
        }

        if (itemId == -1) {
            // Add mode: if an item with this exact (normalised) name already
            // exists, merge quantities into it instead of creating a duplicate row.
            PantryItem existing = pantryDao.getByName(name);

            if (existing != null) {
                double combinedQuantity = existing.getQuantity() + quantity;
                PantryItem merged = new PantryItem(existing.getId(), existing.getName(), combinedQuantity, unit, expiry);
                pantryDao.update(merged);
                Toast.makeText(this, "Quantity updated", Toast.LENGTH_SHORT).show();
            } else {
                PantryItem newItem = new PantryItem(0, name, quantity, unit, expiry);
                pantryDao.insert(newItem);
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            }
        } else {
            PantryItem updatedItem = new PantryItem(itemId, name, quantity, unit, expiry);
            pantryDao.update(updatedItem);
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

}