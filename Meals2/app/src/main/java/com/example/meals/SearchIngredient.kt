package com.example.meals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class SearchIngredient : AppCompatActivity() {
    lateinit var retrieveBtn: Button
    lateinit var saveBtn: Button
    lateinit var searchBox: EditText
    lateinit var textView: TextView
    lateinit var backBtnImg: ImageView
    var mealsKey: String = "mealsKey"
    val strBuilder2 = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_ingredient)

        retrieveBtn = findViewById(R.id.retrieveBtn)
        saveBtn = findViewById(R.id.saveBtn)
        searchBox = findViewById(R.id.searchBox)
        textView = findViewById(R.id.displayTxt)
        backBtnImg = findViewById(R.id.backBtnImg)

        val db = Room.databaseBuilder(this, AppDatabase::class.java,"mealDB").build()
        val mealDao = db.mealDao()

        backBtnImg.setOnClickListener() {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        retrieveBtn.setOnClickListener() {
            strBuilder2.clear()
            retrieveMeals()
        }

        saveBtn.setOnClickListener() {
            saveMeals(mealDao)
        }
    }

    fun retrieveMeals() {
        val strBuilder = StringBuilder("")
        strBuilder.clear()

        // get the user input
        val ingredientEntered = searchBox?.text.toString()

        // check if the input is empty
        if (ingredientEntered.isEmpty()) {
            Toast.makeText(this@SearchIngredient, "Please enter a search query", Toast.LENGTH_SHORT).show()
            return
        }

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=" + ingredientEntered.trim())
                    val connection = url.openConnection()
                    val reader = BufferedReader(InputStreamReader(connection.getInputStream()))

                    var line = reader.readLine()
                    while (line != null) {
                        strBuilder.append(line)
                        line = reader.readLine()
                    }

                    // now do the JSON parsing
                    if (strBuilder.toString().contains("{\"meals\":null}")) {
                        strBuilder2.append("Meal does not exist")
                        return@withContext
                    }

                    val json = JSONObject(strBuilder.toString())
                    val jsonArray = json.getJSONArray("meals")

                    for (i in 0..jsonArray.length()-1) {
                        val json_meals = jsonArray[i] as JSONObject
                        val meal_name = json_meals["strMeal"] as String
                        strBuilder2.append(meal_name + "\n")
                    }
                }
                textView?.setText(strBuilder2)
            }
        }
    }


    fun saveMeals(mealDAO: MealDAO): String {
        val strBuilder = StringBuilder("")
        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val ingredientEntered = searchBox?.text.toString()
                    val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=" + ingredientEntered.trim())
                    val connection = url.openConnection()
                    val reader = BufferedReader(InputStreamReader(connection.getInputStream()))

                    var line = reader.readLine()
                    while (line != null) {
                        strBuilder.append(line)
                        line = reader.readLine()
                    }

                    try {
                        val json = JSONObject(strBuilder.toString())
                        val jsonArray = json.getJSONArray("meals")

                        for (i in 0..jsonArray.length()-1) {
                            val json_meals = jsonArray[i] as JSONObject
                            val meal_id = json_meals["idMeal"] as String
                            val id_meal = meal_id.toInt()
                            val meal_name = json_meals["strMeal"] as String
                            val instructions = json_meals["strInstructions"] as String
                            val meal_thumb = json_meals["strMealThumb"] as String
                            val ingredient1 = json_meals["strIngredient1"] as String
                            val ingredient2 = json_meals["strIngredient2"] as String
                            val ingredient3 = json_meals["strIngredient3"] as String
                            val ingredient4 = json_meals["strIngredient4"] as String
                            val ingredient5 = json_meals["strIngredient5"] as String
                            val ingredient6 = json_meals["strIngredient6"] as String
                            val ingredient7 = json_meals["strIngredient7"] as String
                            val ingredient8 = json_meals["strIngredient8"] as String
                            val ingredient9 = json_meals["strIngredient9"] as String
                            val ingredient10 = json_meals["strIngredient10"] as String
                            val measure1 = json_meals["strMeasure1"] as String
                            val measure2 = json_meals["strMeasure2"] as String
                            val measure3 = json_meals["strMeasure3"] as String
                            val measure4 = json_meals["strMeasure4"] as String
                            val measure5 = json_meals["strMeasure5"] as String
                            val measure6 = json_meals["strMeasure6"] as String
                            val measure7 = json_meals["strMeasure7"] as String
                            val measure8 = json_meals["strMeasure8"] as String
                            val measure9 = json_meals["strMeasure9"] as String
                            val measure10 = json_meals["strMeasure10"] as String
                            val meal = Meals(id_meal, meal_name, instructions, meal_thumb, ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9, ingredient10, measure1, measure2, measure3, measure4, measure5, measure6, measure7, measure8, measure9, measure10)
                            mealDAO.insertMeals(meal)
                        }
                    } catch (e: Exception) {
                        Log.e("JSON", "Error converting JSON to string: ${e.message}")
                        return@withContext
                    }
                }
            }
        }
        Toast.makeText(this@SearchIngredient, "Retrieved Meals have been added to the Database", Toast.LENGTH_SHORT).show()
        return strBuilder.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(mealsKey, strBuilder2.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        strBuilder2.append(savedInstanceState.getString(mealsKey))
        textView?.setText(strBuilder2)
    }
}