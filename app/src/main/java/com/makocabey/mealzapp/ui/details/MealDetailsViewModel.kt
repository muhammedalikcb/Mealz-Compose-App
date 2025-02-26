package com.makocabey.mealzapp.ui.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makocabey.model.MealsRepository
import com.makocabey.model.response.MealResponse

class MealDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MealsRepository = MealsRepository()
) : ViewModel() {

    var mealState = mutableStateOf<MealResponse?>(null)

    init {
        val mealCategoryId: String? = savedStateHandle["meal_category_id"]
        Log.d("QWERTY", "MealDetailsViewModel mealCategoryId: $mealCategoryId")
        mealState.value = repository.getMealById(mealCategoryId)
    }
}