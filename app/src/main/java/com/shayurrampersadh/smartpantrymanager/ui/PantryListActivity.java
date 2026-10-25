package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.data.DbSeeder;
import com.shayurrampersadh.smartpantrymanager.data.PantryDao;
import com.shayurrampersadh.smartpantrymanager.data.RecipeDao;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;
import com.shayurrampersadh.smartpantrymanager.util.NavigationHelper;


import java.util.List;


/**
 * Launcher screen. Shows the pantry list (RecyclerView), seeds the
 * recipe database on first run, and handles add/edit/delete via
 * PantryAdapter's callback interface.
 */
public class PantryListActivity extends AppCompatActivity implements PantryAdapter.OnItemActionListener {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private List<PantryItem> pantryItems;
    private PantryDao pantryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry_list);

        pantryDao = new PantryDao(this);

        new DbSeeder(this).seedIfEmpty();

        recyclerView = findViewById(R.id.rvPantryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPantryItems();

        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        NavigationHelper.setupNavBar(this);
    }


    // Runs every time this screen becomes visible again (including after
    // returning from Add/Edit), so the list always reflects the latest data.
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        pantryItems = pantryDao.getAll();
        adapter = new PantryAdapter(pantryItems, this);
        recyclerView.setAdapter(adapter);

        TextView tvPantryEmptyState = findViewById(R.id.tvPantryEmptyState);
        if (pantryItems.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvPantryEmptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvPantryEmptyState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEdit(PantryItem item) {
        Intent intent = new Intent(this, AddEditIngredientActivity.class);
        intent.putExtra(AddEditIngredientActivity.EXTRA_ITEM_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {
        // The confirmation dialog already happened in PantryAdapter —
        // by the time this fires, the user has confirmed.
        pantryDao.delete(item.getId());
        loadPantryItems();
    }
}