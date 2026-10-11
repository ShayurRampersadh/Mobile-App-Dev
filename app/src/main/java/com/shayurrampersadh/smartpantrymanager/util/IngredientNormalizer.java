package com.shayurrampersadh.smartpantrymanager.util;

public class IngredientNormalizer {
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
}
