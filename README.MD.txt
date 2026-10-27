# Smart Pantry Manager

This is an Android app that helps reduce food waste by tracking what ingredients a user has, and suggests recipes they can make right now using what's already in their pantry.

## Core Feature: Strict-Matching Recipe Suggestions

A recipe is only shown as "Suggested" if every single required ingredient is in the pantry, in a sufficient quantity. Recipes missing exactly one ingredient in an "Almost There" list. Ingredient name matching is normalised so minor differences like "tomato" vs "Tomatoes" don't break the matching.

## Features

- Full pantry CRUD (add, edit, delete, with delete confirmation)
- Expiry tracking, with "Expired" and "Expiring soon" labels (toggleable via Settings)
- Suggested / Almost There recipe tabs, based on live pantry matching
- Recipe detail view with ingredients and step-by-step method
- Settings screen with a persistent expiring-soon alert toggle
- Bottom navigation Pantry, Recipes, and Settings

## Database

This app uses SQLite, accessed through SQLiteOpenHelper, for local, offline-first persistence across three tables: pantry_items, recipes, and recipe_ingredients.

SQLite was chosen because:
- The data is naturally relational (one recipe requires many ingredients)
- The app's data is personal and single-device, with no real need for cloud sync
- It requires no server setup or ongoing internet connection
- It matched the module's curriculum focus, allowing development effort to go toward the matching logic itself rather than unfamiliar backend infrastructure

## Setup and Run

1. Clone this repository:

git clone https://github.com/ShayurRampersadh/Mobile-App-Dev.git

2. Open the project in Android StudiO.
3. Let Gradle sync complete.
4. Run the app on an emulator or physical device.
5. the SQLite database and its 17 seeded recipes are created automatically on first launch.

## Tech Stack

- Java
- Android Studio
- SQLite (SQLiteOpenHelper)
- RecyclerView / Adapter / ViewHolder
- SharedPreferences (Settings)