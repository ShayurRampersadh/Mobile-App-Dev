package com.shayurrampersadh.smartpantrymanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.model.MatchResult;

import java.util.List;

/**
 * RecyclerView.Adapter for a list of recipe matching results. Reused
 * for both the Suggested and Almost There tabs in SuggestedRecipesActivity
 * Which list it shows depends only on what MatchResult list it's
 * constructed with, not on any internal "mode" flag.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<MatchResult> matchResults;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(MatchResult matchResult);
    }

    public RecipeAdapter(List<MatchResult> matchResults, OnRecipeClickListener listener) {
        this.matchResults = matchResults;
        this.listener = listener;
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        TextView tvMissingInfo;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvMissingInfo = itemView.findViewById(R.id.tvMissingInfo);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        MatchResult result = matchResults.get(position);

        holder.tvRecipeName.setText(result.getRecipe().getName());

        if (result.getMissingCount() == 0) {
            holder.tvMissingInfo.setText("Ready to make");
        } else {
            holder.tvMissingInfo.setText("Missing: " + String.join(", ", result.getMissingIngredients()));
        }

        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(result));
    }

    @Override
    public int getItemCount() {
        return matchResults.size();
    }

}