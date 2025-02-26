package com.makocabey.mealzapp.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makocabey.model.MealsRepository
import com.makocabey.model.response.MealResponse
import kotlinx.coroutines.launch

class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository()
) : ViewModel() {


    init {
        viewModelScope.launch {
            val meals = getMeals()
            mealState.value = meals
        }
    }

    val mealState: MutableState<List<MealResponse>> = mutableStateOf(emptyList())

    private suspend fun getMeals(): List<MealResponse> {
        return repository.getMeals().categories
    }
}