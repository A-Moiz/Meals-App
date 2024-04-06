package com.example.meals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class SearchWebService : AppCompatActivity() {
    lateinit var searchBtn: Button
    lateinit var searchBox: EditText
    lateinit var backBtnImg: ImageView
    lateinit var displayMeals: TextView

    var mealsKey: String = "mealsKey"
    val strBuilder = StringBuilder("")
    val strBuilder2 = StringBuilder("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_web_service)

        searchBox = findViewById(R.id.searchBox)
        searchBtn = findViewById(R.id.searchBtn)
        backBtnImg = findViewById(R.id.backBtnImg)
        displayMeals = findViewById(R.id.displayTxt)

        backBtnImg.setOnClickListener() {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        searchBtn.setOnClickListener() {
            strBuilder2.clear()
            search()
        }
    }

    private fun search() {
        strBuilder.clear()
        val userInput = searchBox?.text.toString().trim()
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please enter a meal name", Toast.LENGTH_SHORT).show()
            return
        }

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$userInput")
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
                        strBuilder2.append("Meal: $meal_name\n")
                    }
                }
                displayMeals?.setText(strBuilder2)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(mealsKey, strBuilder2.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        strBuilder2.append(savedInstanceState.getString(mealsKey))
        displayMeals?.setText(strBuilder2)
    }
}