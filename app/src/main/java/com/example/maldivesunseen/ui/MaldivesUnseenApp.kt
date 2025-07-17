package com.example.maldivesunseen.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maldivesunseen.R

// Navigation destinations using sealed classes
sealed class Destination(
    val route: String,
    @StringRes val label: Int? = null,
    val icon: ImageVector? = null,
    @StringRes val contentDescription: Int? = null
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
        icon = Icons.AutoMirrored.Default.List, // TODO: Check if it's a bulleted list, if not add bulleted list icon
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

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) { // QUESTION: Why do we need windowInsets?
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
                        contentDescription =  stringResource(destination.contentDescription ?: R.string.home_description)
                    )
                },
                label = {
                    Text(stringResource(destination.label ?: R.string.home))
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
            composable(Destination.Settings.route) {
                SettingsScreen()
            }
    }
}

@Composable
fun MaldivesUnseenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
    ) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = modifier
    ) {
        paddingValues ->
        AppNavHost(
            navController = navController,
            modifier = modifier.padding(paddingValues)
        )
    }
}
