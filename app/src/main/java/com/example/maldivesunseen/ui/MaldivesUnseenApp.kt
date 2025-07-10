package com.example.maldivesunseen.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maldivesunseen.R

// Is it necessary to create string values for the dynamic screens such as CategoryDetail and Recommendation?
enum class Destination(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Categories(title = R.string.category),
    CategoryDetail(title = R.string.category_detail),
    Recommendation(title = R.string.recommendation),
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home,
        modifier = modifier
    ) {
            composable(Destination.Home.name) {
                HomeScreen()
            }
            composable(Destination.Categories.name) {
                CategoriesScreen()
            }
            composable(Destination.CategoryDetail.name) {
                CategoryDetailScreen()
            }
            composable(Destination.Recommendation.name) {
                RecommendationScreen()
            }
    }
}

@Composable
fun MaldivesUnseenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
    ) {
    Scaffold {
        paddingValues ->
        AppNavHost(
            navController = navController,
            modifier = modifier.padding(paddingValues)
        )
    }
}
