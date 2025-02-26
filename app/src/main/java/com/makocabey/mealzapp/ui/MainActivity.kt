package com.makocabey.mealzapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.makocabey.mealzapp.ui.details.MealDetailScreen
import com.makocabey.mealzapp.ui.details.MealDetailsViewModel
import com.makocabey.mealzapp.ui.meals.MealsCategoriesScreen
import com.makocabey.mealzapp.ui.meals.MealsCategoriesViewModel
import com.makocabey.mealzapp.ui.theme.MealzAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MealzAppTheme {
                FoodiezApp()
            }
        }
    }
}

@Composable
private fun FoodiezApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "destionation_meals_list") {
        composable(route = "destionation_meals_list") {
            MealsCategoriesScreen() { navigationMealId ->
                Log.d("QWERTY", "navigationMealId: $navigationMealId")
                navController.navigate("destination_meal_details/$navigationMealId")
            }
        }

        composable(
            route = "destination_meal_details/{meal_category_id}",
            arguments = listOf(navArgument("meal_category_id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val mealCategoryId = backStackEntry.arguments?.getString("meal_category_id")
            Log.d("QWERTY", "backStackEntry mealCategoryId: $mealCategoryId")
            val viewModel = viewModel<MealDetailsViewModel>()
            MealDetailScreen(viewModel.mealState.value)
        }
    }
}