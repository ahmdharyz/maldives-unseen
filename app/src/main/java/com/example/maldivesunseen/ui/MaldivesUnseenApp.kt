package com.example.maldivesunseen.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.maldivesunseen.R

// Is it necessary to create string values for the dynamic screens such as CategoryDetail and Recommendation?
enum class MaldivesUnseenApp(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Categories(title = R.string.category),
    CategoryDetail(title = R.string.category_detail),
    Recommendation(title = R.string.recommendation),
}

@Composable
fun MaldivesUnseenApp(modifier: Modifier = Modifier) {
    Scaffold {
        paddingValues ->
        modifier.padding(paddingValues)

        NavHost(
            navController = navController,
            startDestination = MaldivesUnseenApp.Home.name,
            modifier = modifier
        )
    }
}
