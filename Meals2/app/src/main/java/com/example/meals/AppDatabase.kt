package com.example.meals

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meals::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDAO
}