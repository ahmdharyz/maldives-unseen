package com.example.maldivesunseen.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maldivesunseen.R

// Navigation destinations using sealed classes
sealed class Destination(
    val route: String,
    val label: Int? = null,
    val icon: ImageVector? = null,
    val contentDescription: Int? = null
) {
    // Bottom navigation destinations
    data object Home: Destination(
        route = "Home",
        label = R.string.home,
        icon = Icons.Default.Home,
        contentDescription = R.string.home_description
    )

    data object Categories: Destination(
        route = "Categories",
        label = R.string.category,
        icon = Icons.AutoMirrored.Default.List, // TODO: Check if it's a bulleted list, if not add butteted list icon
        contentDescription = R.string.category_description
    )

    data object Settings: Destination(
        route = "Settings",
        label = R.string.settings,
        icon = Icons.Default.Settings,
        contentDescription = R.string.settings_description
    )

    // Non-Navigation Bar destinations
    data object CategoryDetail: Destination(
        route = "Category/{categoryId}",
    ) {
        fun createRoute(categoryId: Int) = "Category/$categoryId"
    }

    data object Recommendation: Destination(
        route = "Recommendation/{recommendationId}",
    ) {
        fun createRoute(recommendationId: Int) = "Recommendation/$recommendationId"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier = modifier
    ) {
            composable(Destination.Home.route) {
                HomeScreen()
            }
            composable(Destination.Categories.route) {
                CategoriesScreen()
            }
            composable(Destination.CategoryDetail.route) {
                CategoryDetailScreen()
            }
            composable(Destination.Recommendation.route) {
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
