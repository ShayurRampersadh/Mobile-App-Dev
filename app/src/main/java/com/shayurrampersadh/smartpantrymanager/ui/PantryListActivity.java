package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.data.DbSeeder;
import com.shayurrampersadh.smartpantrymanager.data.PantryDao;
import com.shayurrampersadh.smartpantrymanager.data.RecipeDao;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;

import java.util.List;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        pantryItems = pantryDao.getAll();
        adapter = new PantryAdapter(pantryItems, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEdit(PantryItem item) {
        Intent intent = new Intent(this, AddEditIngredientActivity.class);
        intent.putExtra(AddEditIngredientActivity.EXTRA_ITEM_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {
        pantryDao.delete(item.getId());
        loadPantryItems();
    }
}