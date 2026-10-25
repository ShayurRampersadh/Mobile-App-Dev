package com.shayurrampersadh.smartpantrymanager.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.shayurrampersadh.smartpantrymanager.R;
import com.shayurrampersadh.smartpantrymanager.model.PantryItem;
import com.shayurrampersadh.smartpantrymanager.util.IngredientNormalizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * RecyclerView.Adapter for the pantry list. Displays each PantryItem as
 * a row (item_pantry.xml), shows expired/expiring-soon status, and
 * reports edit/delete taps back to PantryListActivity via the
 * OnItemActionListener callback — this Adapter never talks to the
 * database or launches any screen directly.
 */
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private OnItemActionListener listener;

    public PantryAdapter(List<PantryItem> pantryItems, OnItemActionListener listener) {
        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    /** Holds references to one row's views so findViewById only runs once per row. */
    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemQuantity;
        TextView tvItemExpiry;
        ImageButton btnEdit;
        ImageButton btnDelete;


        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
            tvItemExpiry = itemView.findViewById(R.id.tvItemExpiry);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Inflates item_pantry.xml into a real View and wraps it in a ViewHolder.
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    // Fills one row's views with the data for the item at this position.
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);
        Context context = holder.itemView.getContext();

        holder.tvItemName.setText(item.getName());
        holder.tvItemQuantity.setText(IngredientNormalizer.formatQuantity(item.getQuantity()) + " " + item.getUnit());

        String expiryDate = item.getExpiryDate();
        if (expiryDate != null && !expiryDate.isEmpty()) {
            if (isExpired(expiryDate)) {
                holder.tvItemExpiry.setText("Expired - was due " + expiryDate);
                holder.tvItemExpiry.setTextColor(Color.RED);
            } else if (isExpiringSoon(expiryDate, context)) {
                holder.tvItemExpiry.setText("Expiring soon: " + expiryDate);
                holder.tvItemExpiry.setTextColor(Color.parseColor("#E65100"));
            } else {
                holder.tvItemExpiry.setText("Expires: " + expiryDate);
                holder.tvItemExpiry.setTextColor(Color.DKGRAY);
            }
        } else {
            holder.tvItemExpiry.setText("");
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(item));
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete item")
                    .setMessage("Are you sure you want to delete \"" + item.getName() + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> listener.onDelete(item))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    /** True if the expiry date (YYYY-MM-DD) is strictly before today. */
    private boolean isExpired(String expiryDate) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return expiryDate.compareTo(today) < 0;
    }

    /**
     * True if the item expires within the next 3 days AND the Settings
     * "expiring-soon alerts" toggle is on. Reads the same SharedPreferences
     * key SettingsActivity writes to — no separate storage needed.
     */
    private boolean isExpiringSoon(String expiryDate, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE);
        boolean alertsEnabled = prefs.getBoolean(SettingsActivity.KEY_EXPIRY_ALERT, true);

        if (!alertsEnabled) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        Calendar threshold = Calendar.getInstance();
        threshold.add(Calendar.DAY_OF_YEAR, 3);
        String thresholdDate = sdf.format(threshold.getTime());

        // String comparison works here because YYYY-MM-DD sorts identically
        // whether compared as text or as real dates.
        return expiryDate.compareTo(today) >= 0 && expiryDate.compareTo(thresholdDate) <= 0;
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

}