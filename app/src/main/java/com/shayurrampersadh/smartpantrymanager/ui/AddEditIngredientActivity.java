package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.data.PantryDao;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;


public class AddEditIngredientActivity extends AppCompatActivity {
        public static final String EXTRA_ITEM_ID = "extra_item_id";

        private EditText etName, etQuantity, etUnit, etExpiry;

        private PantryDao pantryDao;
        private int itemId = -1;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_ingredient);

        pantryDao = new PantryDao(this);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        Button btnSave = findViewById(R.id.btnSave);

        itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);

        if (itemId != -1){
            loadExistingItem();
        }
        btnSave.setOnClickListener(view -> saveItem());
    }

    private void loadExistingItem(){
        PantryItem item = pantryDao.getById(itemId);

        if(item != null){
            etName.setText(item.getName());
            etQuantity.setText(String.valueOf(item.getQuantity()));
            etUnit.setText(item.getUnit());
            etExpiry.setText(item.getExpiryDate());
        }
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
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
            PantryItem newItem = new PantryItem(0, name, quantity, unit, expiry);
            pantryDao.insert(newItem);
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        } else {
            PantryItem updatedItem = new PantryItem(itemId, name, quantity, unit, expiry);
            pantryDao.update(updatedItem);
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

}
