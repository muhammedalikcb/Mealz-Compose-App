package com.makocabey.model

import com.makocabey.model.api.MealsWebService
import com.makocabey.model.response.MealResponse
import com.makocabey.model.response.MealsCategoriesResponse

class MealsRepository(
    private val webService: MealsWebService = MealsWebService()
) {

    private var cachedMeals = listOf<MealResponse>()

    suspend fun getMeals() : MealsCategoriesResponse {
        val response = webService.getMeals()
        cachedMeals = response.categories
        return response
    }

    fun getMealById(id: String?) : MealResponse? {
        return cachedMeals.firstOrNull() {
            it.id == id
        }
    }
}