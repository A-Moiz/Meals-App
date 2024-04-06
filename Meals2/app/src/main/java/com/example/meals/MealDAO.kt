package com.example.meals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDAO {
    @Query("Select * from Meals")
    suspend fun getAll(): List<Meals>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(vararg meal: Meals)

    @Insert
    suspend fun insertAll(vararg meals: Meals)

    @Query("DELETE FROM Meals")
    suspend fun deleteAll()
}