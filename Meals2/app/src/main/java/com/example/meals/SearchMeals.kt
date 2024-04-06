package com.example.meals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchMeals : AppCompatActivity() {
    // Search box
    lateinit var searchBox: EditText

    // Text view
    lateinit var displayMeals: TextView

    // Button
    lateinit var retrieveBtn: Button

    // Back button image
    lateinit var backBtn: ImageView

    // List of meals
    lateinit var meals: List<Meals>

    // Key variable
    var mealsKey: String = "mealsKey"

    // String builder
    val strBuilder = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meals)

        // Getting IDs of components in activity
        searchBox = findViewById(R.id.searchBox)
        displayMeals = findViewById(R.id.displayMeals)
        retrieveBtn = findViewById(R.id.retrieveBtn)
        backBtn = findViewById(R.id.backBtnImg)

        // DB and DAO
        val db = Room.databaseBuilder(this, AppDatabase::class.java,"mealDB").build()
        val mealDao = db.mealDao()

        backBtn.setOnClickListener() {
            backToHome()
        }

        retrieveBtn.setOnClickListener() {
            strBuilder.clear()
            searchMeals(mealDao)
        }
    }

    private fun searchMeals(mealDAO: MealDAO) {
        lifecycleScope.launch(Dispatchers.IO) {
            meals = mealDAO.getAll()
            val userInput = searchBox.text.toString()

            if (userInput == "") {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SearchMeals, "Please enter a valid meal name or ingredient", Toast.LENGTH_SHORT).show()
                }
            } else {
                for (meals in meals) {
                    if ((meals.Meal.toString().contains(userInput, true) || (meals.Ingredient1.toString().contains(userInput, true)) || (meals.Ingredient2.toString().contains(userInput, true)) || (meals.Ingredient3.toString().contains(userInput, true)) || (meals.Ingredient4.toString().contains(userInput, true)) || (meals.Ingredient5.toString().contains(userInput, true)) || (meals.Ingredient6.toString().contains(userInput, true)) || (meals.Ingredient7.toString().contains(userInput, true)) || (meals.Ingredient8.toString().contains(userInput, true)) || (meals.Ingredient9.toString().contains(userInput, true)) || (meals.Ingredient10.toString().contains(userInput, true)))) {
                        strBuilder.append("<b>Meal:</b> " + meals.Meal + "<br><b>Ingredient 1:</b> " + meals.Ingredient1 + "<br><b>Ingredient 2:</b> " + meals.Ingredient2 + "<br><b>Ingredient 3:</b> " + meals.Ingredient3 + "<br><b>Ingredient 4:</b> " + meals.Ingredient4 + "<br><b>Ingredient 5:</b> " + meals.Ingredient5 + "<br><b>Ingredient 6:</b> " + meals.Ingredient6 + "<br><b>Ingredient 7:</b> " + meals.Ingredient7 + "<br><b>Ingredient 8:</b> " + meals.Ingredient8 + " <br><b>Ingredient 9:</b> " + meals.Ingredient9 + " <br><b>Ingredient 10:</b> " + meals.Ingredient10 +"<br><br>")
                        withContext(Dispatchers.Main) {
                            displayMeals.text = HtmlCompat.fromHtml(strBuilder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                        }
                    } else {
                        strBuilder.append("No meals in DB with $userInput as name or ingredient<br>")
                    }
                }
            }
        }
    }

    private fun backToHome() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(mealsKey, strBuilder.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        strBuilder.append(savedInstanceState.getString(mealsKey))
        displayMeals.setText(strBuilder)
    }
}