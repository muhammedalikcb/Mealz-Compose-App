# MealzApp

A small Android sample app that lists meal categories from [TheMealDB](https://www.themealdb.com/) API. It's built with Jetpack Compose, MVVM, Kotlin coroutines and Retrofit.

## What it does

- Fetches meal categories from `https://www.themealdb.com/api/json/v1/1/categories.php` and shows them in a `LazyColumn`.
- Each card shows the category image (loaded with Coil), its name, and a description you can expand or collapse with the arrow icon.
- Tapping a card navigates to a detail screen with Navigation Compose. The detail screen is still a work in progress.

## Architecture

The app uses MVVM to keep the UI separate from state and data.

- View: `MealsCategoriesScreen` shows the list of categories and `MealDetailScreen` shows a single one.
- ViewModel: `MealsCategoriesViewModel` loads the categories and exposes them as Compose state. `MealDetailsViewModel` reads the selected category id from `SavedStateHandle`.
- Model: `MealsRepository` wraps the Retrofit service (`MealsWebService`). `MealsCategoriesResponse` and `MealResponse` map the JSON.

There is no separate use-case layer. The ViewModel calls the repository directly, which is enough for an app this size.

### Coroutines

Data is loaded in `viewModelScope`, so the request is cancelled automatically when the ViewModel is cleared.

```kotlin
viewModelScope.launch {
    val meals = getMeals()
    mealState.value = meals
}
```

When the response arrives, `mealState` is updated and Compose redraws the list.

### Dependency injection

There's no DI framework like Hilt or Dagger. Dependencies are passed as constructor default parameters, for example `MealsCategoriesViewModel(repository: MealsRepository = MealsRepository())`. That keeps the setup easy to follow and still makes it simple to swap in a fake repository.

### Networking

Retrofit with the Gson converter. The API interface uses `suspend` functions, so calls run inside coroutines without callbacks.

```kotlin
suspend fun getMeals(): MealsCategoriesResponse {
    return webService.getMeals()
}
```

## Testing

Only the default template tests are in the project so far (`ExampleUnitTest` and `ExampleInstrumentedTest`). There are no ViewModel or repository tests yet.

## Project structure

```
app/src/main/java/com/makocabey
├── mealzapp/ui
│   ├── MainActivity.kt          # Activity and NavHost (list -> details)
│   ├── meals                    # Categories screen and ViewModel
│   ├── details                  # Detail screen and ViewModel
│   └── theme                    # Colors, typography, theme
└── model
    ├── MealsRepository.kt       # Repository, caches the last fetched list
    ├── api/MealsApi.kt          # Retrofit service and API interface
    └── response/Responses.kt    # API response models
```

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/muhammedalikcb/Mealz-Compose-App.git
   ```
2. Open the project in Android Studio and let Gradle sync. Dependencies are resolved automatically.
3. Run it on an emulator or a physical device. The device needs an internet connection because all data comes from the API.

Build settings: compileSdk and targetSdk 34, minSdk 25, Kotlin 1.9.0, Android Gradle Plugin 8.6.1.

## Dependencies

Versions are defined in `gradle/libs.versions.toml`.

- Jetpack Compose (BOM 2024.10.00) with Material 3
- Navigation Compose 2.8.3
- Lifecycle ViewModel Compose and Runtime KTX 2.8.6
- Retrofit 2.11.0 with the Gson converter 2.11.0 (`com.squareup.retrofit2:retrofit`, `com.squareup.retrofit2:converter-gson`)
- Coil 2.0.0 (`io.coil-kt:coil-compose`) for image loading
- Wear Compose Material 1.4.0, used only for `ContentAlpha`
- Kotlin coroutines, which come in through the AndroidX lifecycle libraries
- AndroidX Core KTX 1.13.1 and Activity Compose 1.9.3

## Code samples

These are simplified versions of the composables in `ui/meals/MealsCategoriesScreen.kt`. The real screen also takes a navigation callback, and each card has the expandable description.

### MealsCategoriesScreen

```kotlin
@Composable
fun MealsCategoriesScreen(viewModel: MealsCategoriesViewModel = viewModel()) {
    val meals = viewModel.mealState.value

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(meals) { meal ->
            MealCategory(meal)
        }
    }
}

```

### MealCategory

Each category is a `Card` with the image on the left and the name next to it, laid out with `Row` and `Column`.

```kotlin
@Composable
fun MealCategory(meal: MealResponse) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(meal.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(88.dp).padding(4.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp)
            ) {
                Text(text = meal.name, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

```
