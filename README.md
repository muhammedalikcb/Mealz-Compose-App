# MealzApp Project

## Overview

The **MealzApp** project is a sample application designed to showcase essential Android development skills using modern technologies and architectural patterns. This project leverages **MVVM architecture**, **Kotlin Coroutines** for asynchronous processing, and **Jetpack Compose** for creating a dynamic and user-friendly UI. The application integrates **Retrofit** for efficient data fetching from an external API, following best practices for a modular, testable, and maintainable codebase.

## Technologies and Architectural Patterns

### MVVM (Model-View-ViewModel)

The **MVVM** pattern is implemented to separate the UI from business logic and data, enhancing code maintainability and modularity.

- **View**: The main UI is displayed in `MealsCategoriesScreen`, which shows meal categories in a scrollable list.
- **ViewModel**: The `MealsCategoriesViewModel` class contains the core logic for fetching and processing data, serving as a bridge between the UI and the model.
- **Model**: Data models like `MealResponse` represent the structure of the data fetched from the API, allowing for seamless data integration in the UI.

### Use Cases

While a formal use-case pattern is not applied here, the `MealsCategoriesViewModel` handles critical data-fetching tasks, maintaining a clean separation of responsibilities to ensure code simplicity and testability.

### Coroutines for Asynchronous Data Fetching

Kotlin Coroutines are used for asynchronous data fetching, ensuring smooth and responsive interactions within the UI. The `viewModelScope` in `MealsCategoriesViewModel` provides a lifecycle-aware coroutine scope that terminates automatically when the ViewModel is cleared.

```kotlin
viewModelScope.launch {
    val meals = getMeals()
    mealState.value = meals
}
```


This coroutine fetches meal data in a non-blocking way, updating mealState to display new data once it's available.

## Dependency Injection (DI)
To keep the codebase straightforward and easy to understand, the project does not use a DI framework like Hilt or Dagger. Instead, dependencies are created and managed manually, offering a simpler setup and reducing complexity for beginners.

## Retrofit for Networking
Retrofit is used to fetch data from a remote API, with a Gson converter for JSON parsing. Integrating Retrofit with coroutines allows asynchronous calls to the API without callbacks, simplifying the networking code.

```kotlin
suspend fun getMeals(): MealsCategoriesResponse {
    return webService.getMeals()
}
```


## Testing Strategy
The MealzApp includes unit tests to validate data processing and business logic in the ViewModel. This approach ensures that core functionalities work as expected.

## Dynamic Data Fetching
MealzApp dynamically fetches data from an external API to display a list of meal categories in the UI. This data retrieval is achieved through coroutines, enabling non-blocking operations for a smooth user experience.

## Project Structure
The project is organized into separate packages to promote modularity and readability:
```
com.makocabey.mealzapp
├── data
│   ├── api               # Network API interfaces
│   ├── model             # Data models for API responses
│   └── repository        # Data repository for API data
├── ui
│   ├── meals             # UI screens and ViewModel for displaying meal categories
│   └── theme             # App theme configuration (colors, typography)
├── utils                 # Utilities and helper classes
└── MealzApp.kt           # Main application class
```

## Setup Instructions
To run this project locally, follow these steps:

- Clone the repository:
```
git clone https://github.com/makocabey/MealzApp.git
```

- Open the project in Android Studio.

- Sync the project with Gradle files:

- Android Studio should automatically handle dependencies and setup.
- Run the application on an emulator or a physical device:

- Ensure the device has an internet connection for the API data to load.


## Dependencies
The following libraries and tools are used in MealzApp:

Jetpack Compose: androidx.compose.* (for UI building)
Kotlin Coroutines: org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0 (for asynchronous operations)
Retrofit: com.squareup.retrofit2:retrofit:2.9.0 (for network communication)
Gson Converter: com.squareup.retrofit2:converter-gson:2.9.0 (for JSON parsing)
Coil: io.coil-kt:coil-compose:1.4.0 (for image loading in Compose)
AndroidX Libraries: Core KTX, AppCompat, Material, Lifecycle ViewModel, and LiveData
Core Features
Dynamic Meal Categories List
The Meals Categories Screen displays a list of meal categories, each with an image and name, using Jetpack Compose's LazyColumn for efficient and smooth scrolling.

## Sample Code for MealsCategoriesScreen
```kotlint
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

## Detailed Meal Category Item
Each meal category item is displayed in a Card component, with an image on the left and the meal name beside it. This layout uses Compose’s Row and Column for alignment and spacing.

Sample Code for MealCategory
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
