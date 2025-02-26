package com.makocabey.mealzapp.ui.meals

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ContentAlpha
import coil.compose.rememberImagePainter
import com.makocabey.mealzapp.ui.theme.MealzAppTheme
import com.makocabey.model.response.MealResponse

@Composable
fun MealsCategoriesScreen(navigationCallback: (String) -> Unit) {
    val viewModel = viewModel<MealsCategoriesViewModel>()

    val meals = viewModel.mealState.value

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(meals) { meal ->
            MealCategory(meal, navigationCallback)
            Log.d("QWERTY", "MealCategory MealCategory: ${meal.id}")
        }
    }
}


@Composable
fun MealCategory(meal: MealResponse, navigationCallback: (String) -> Unit) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                navigationCallback(meal.id)
                Log.d("QWERTY", "navigationCallback MealCategory: ${meal.id}")
            }
    ) {
        Row(
            modifier = Modifier.animateContentSize()
        ) {
            Image(
                painter = rememberImagePainter(meal.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(16.dp)
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = meal.description,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if  (isExpanded) 10 else 4,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            }

            Icon(
                imageVector = if(isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Expand",
                modifier = Modifier
                    .padding(16.dp)
                    .align(
                        if(isExpanded)
                            Alignment.Bottom
                        else
                            Alignment.CenterVertically
                    )
                    .clickable{ isExpanded = !isExpanded }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealzAppTheme {
        MealsCategoriesScreen( {} )
    }
}