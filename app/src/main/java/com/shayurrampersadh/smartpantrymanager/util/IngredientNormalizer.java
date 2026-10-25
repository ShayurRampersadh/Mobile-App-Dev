package com.shayurrampersadh.smartpantrymanager.util;

/**
 * Small, stateless helpers for displaying and comparing ingredient
 * names and quantities consistently across the app. All methods are
 * static — this class holds no data of its own.
 */
public class IngredientNormalizer {

    /**
     * Standardises an ingredient name for COMPARISON purposes only
     * (used internally by RecipeMatcher, never shown on screen as-is).
     * Lowercases, trims whitespace, and strips a simple trailing "s"
     * or "es" so "Tomatoes" and "tomato" compare as identical. This is
     * a heuristic, not full NLP — it won't catch irregular plurals.
     */
    public static String normalize(String ingredientName) {
        if (ingredientName == null) {
            return "";
        }
        String normalized = ingredientName.trim().toLowerCase();

        if (normalized.endsWith("es")) {
            normalized = normalized.substring(0, normalized.length() - 2);
        } else if (normalized.endsWith("s") && !normalized.endsWith("ss")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return normalized;



    }

    /**
     * Formats a quantity for DISPLAY. Java's default double-to-String
     * conversion always shows a decimal point (2 becomes "2.0"), so
     * this only keeps the decimal when the number is a genuine fraction.
     */
    public static String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity)) {
            return String.valueOf((int) quantity);
        } else {
            return String.valueOf(quantity);
        }
    }

    /**
     * Converts a pantry item name to Title Case before it's saved
     * This is what lets PantryDao.getByName() reliably find an existing item
     * regardless of how the user typed the name, so duplicates merge
     * instead of creating separate rows.
     */
    public static String toTitleCase(String name) {
        if (name == null) {
            return "";
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }
        String[] words = trimmed.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
