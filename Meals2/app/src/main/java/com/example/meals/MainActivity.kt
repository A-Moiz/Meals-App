package com.example.meals

// Link to video: https://drive.google.com/file/d/1oPyREMnc4I-hKv7sqSR11mzn2aY17xmt/view?usp=sharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    // Buttons in activity
    lateinit var addMeals: Button
    lateinit var searchIngredients: Button
    lateinit var searchMeals: Button
    lateinit var deleteMeals: Button
    lateinit var searchWebService: Button

    // Meal ID
    var mealID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Buttons
        addMeals = findViewById(R.id.addBtn)
        searchIngredients = findViewById(R.id.ingredientsBtn)
        searchMeals = findViewById(R.id.mealsBtn)
        deleteMeals = findViewById(R.id.deleteBtn)
        searchWebService = findViewById(R.id.searchWebService)

        // DB and DAO
        val db = Room.databaseBuilder(this, AppDatabase::class.java,"mealDB").build()
        val mealDao = db.mealDao()

        addMeals.setOnClickListener() {
            addingMeals(mealDao)
        }

        searchIngredients.setOnClickListener() {
            searchForIngredients()
        }

        searchMeals.setOnClickListener() {
            searchForMeals()
        }

        deleteMeals.setOnClickListener() {
            deleteAllMeals(mealDao)
        }

        searchWebService.setOnClickListener() {
            searchMealsInWebService()
        }
    }

    private fun searchMealsInWebService() {
        var intent = Intent(this, SearchWebService::class.java)
        startActivity(intent)
    }

    private fun deleteAllMeals(mealDAO: MealDAO) {
        CoroutineScope(Dispatchers.IO).launch {
            mealDAO.deleteAll()
        }
        Toast.makeText(this, "All Meals have been deleted from the Database", Toast.LENGTH_SHORT).show()
    }

    private fun searchForMeals() {
        var intent = Intent(this, SearchMeals::class.java)
        startActivity(intent)
    }

    private fun searchForIngredients() {
        var intent = Intent(this, SearchIngredient::class.java)
        startActivity(intent)
    }

    private fun addingMeals(mealDAO: MealDAO) {
        runBlocking {
            launch {
                val mealOne = Meals(mealID+1, "Sweet and Sour Pork", "Preparation\\r\\n1. Crack the egg into a bowl. Separate the egg white and yolk.\\r\\n\\r\\nSweet and Sour Pork\\r\\n2. Slice the pork tenderloin into ips.\\r\\n\\r\\n3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\\r\\n\\r\\n4. Marinade the pork ips for about 20 minutes.\\r\\n\\r\\n5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\\r\\n\\r\\nSweet and Sour Pork\\r\\nCooking Inuctions\\r\\n1. Pour the cooking oil into a wok and heat to 190\\u00b0C (375\\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\\r\\n\\r\\n2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\\r\\n\\r\\n3. Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\\r\\n\\r\\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\\r\\n\\r\\n5. Serve on a plate and add some coriander for decoration.", "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/1529442316.jpg", "Pork", "Egg", "Water", "Salt", "Sugar", "Soy Sauce", "Starch", "Tomato Puree", "Vinegar", "Coriander", "200g", "1", "Dash", "1\\/2 tsp", "1 tsp", "10g", "10g", "30g", "10g", "Dash")

                val mealTwo = Meals(mealID+2, "Chicken Marengo", "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\\r\\nPour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \\u2013 you shouldn\\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.", "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/qpxvuq1511798906.jpg", "Olive Oil", "Mushrooms", "Chicken Legs", "Passata", "Chicken Stock Cube", "Black Olives", "Parsley", null, null, null, "1 tbs", "300g", "4", "500g", "1", "100g", "Chopped", null, null, null)

                val mealThree = Meals(mealID+3, "Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber", "Add'l ingredients: mayonnaise, siracha\\r\\n\\r\\n1\\r\\n\\r\\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve.\\r\\n\\r\\n2\\r\\n\\r\\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\\r\\n\\r\\n3\\r\\n\\r\\nIn a medium bowl, combine cucumber, juice from half the lime, \\u00bc tsp sugar (\\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\\u2019d like. Season with salt and pepper.\\r\\n\\r\\n4\\r\\n\\r\\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\\r\\n\\r\\n5\\r\\n\\r\\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.", "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/z0ageb1583189517.jpg", "Rice", "Onion", "Lime", "Garlic Clove", "Cucumber", "Carrots", "Ground Beef", "Soy Sauce", null, null, "White", "1", "1", "3", "1", "3 oz", "1 lb", "2 oz", null, null)

                val mealFour = Meals(mealID+4, "Leblebi Soup", "Heat the oil in a large pot. Add the onion and cook until translucent.\\r\\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes.\\r\\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste.\\r\\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\\r\\nSeason to taste with salt, pepper and lemon juice and serve hot.", "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/x2fw9e1560460636.jpg", "Olive Oil", "Onion", "Chickpeas", "Vegetable Stock", "Cumin", "Garlic", "Salt", "Harissa Spice", "Pepper", "Lime", "2 tbs", "1 medium finely diced", "250g", "1.5L", "1 tsp", "5 cloves", "1\\/2 tsp", "1 tsp", "Pinch", "1\\/2")
                mealDAO.insertMeals(mealOne, mealTwo, mealThree, mealFour)
            }
        }
        Toast.makeText(this, "Meals have been added to the DB", Toast.LENGTH_SHORT).show()
    }
}