package com.example.maldivesunseen.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun BottomNavigationBar(navController: NavHostController) {
    val destinations = listOf(
        Destination.Home,
        Destination.Categories,
        Destination.Settings
    )

    val startDestination = Destination.Home
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.route) }

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) { // TODO: Check what happens when you remove windowInsets
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = {
                    navController.navigate(destination.route)
                    selectedDestination = destination.route
                },
                icon = {
                    Icon(
                        imageVector = destination.icon ?: Icons.Default.Home,
                        contentDescription =  destination.contentDescription.toString()
                    )
                }
            )
        }
    }
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
