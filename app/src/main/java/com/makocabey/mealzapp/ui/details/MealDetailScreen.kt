package com.makocabey.mealzapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.makocabey.model.response.MealResponse

@Composable
fun MealDetailScreen(meal: MealResponse?) {
    Column {
        Row {
            Card {
                Image(
                    painter = rememberImagePainter(meal?.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
                Text(
                    meal?.name ?: "default name",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Button(onClick = { /*TODO*/ } ) {
                Text("Change state of meal profile picture")
            }
        }
    }
}