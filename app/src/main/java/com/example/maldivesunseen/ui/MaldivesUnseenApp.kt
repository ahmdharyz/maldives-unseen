package com.example.maldivesunseen.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
        icon = Icons.AutoMirrored.Default.List,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaldivesUnseenAppTopBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(currentScreen)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
    )
}

@Composable
fun BottomNavigationBar(
    currentScreen: String,
    navController: NavHostController
) {
    val destinations = listOf(
        Destination.Home,
        Destination.Categories,
        Destination.Settings
    )

    var currentRoute by rememberSaveable { mutableStateOf(currentScreen) }

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) { // QUESTION: Why do we need windowInsets?
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentScreen == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        // Question: Should I use popUpTo? Would this be considered a circular navigation?
                        // https://developer.android.com/guide/navigation/backstack/circular
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    currentRoute = destination.route
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: Destination.Home.route

    Scaffold(
        topBar = {
            MaldivesUnseenAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                modifier = modifier
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                navController = navController
            )
        },
        modifier = modifier
    ) {
        innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun MaldivesUnseenAppPreview() {
    MaldivesUnseenApp()
}