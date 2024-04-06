package com.example.meals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meals(
    @PrimaryKey val idMeal: Int,
    val Meal: String?,
    val Instructions: String?,
    val MealThumb: String?,
    val Ingredient1: String?,
    val Ingredient2: String?,
    val Ingredient3: String?,
    val Ingredient4: String?,
    val Ingredient5: String?,
    val Ingredient6: String?,
    val Ingredient7: String?,
    val Ingredient8: String?,
    val Ingredient9: String?,
    val Ingredient10: String?,
    val Measure1: String?,
    val Measure2: String?,
    val Measure3: String?,
    val Measure4: String?,
    val Measure5: String?,
    val Measure6: String?,
    val Measure7: String?,
    val Measure8: String?,
    val Measure9: String?,
    val Measure10: String?,
)