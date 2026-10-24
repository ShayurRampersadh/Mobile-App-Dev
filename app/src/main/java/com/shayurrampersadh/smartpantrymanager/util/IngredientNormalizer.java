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

    public static String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity)) {
            return String.valueOf((int) quantity);
        } else {
            return String.valueOf(quantity);
        }
    }

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
