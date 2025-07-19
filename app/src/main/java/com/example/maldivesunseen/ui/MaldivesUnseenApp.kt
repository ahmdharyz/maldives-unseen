package com.example.maldivesunseen.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MaldivesUnseenApp(modifier: Modifier = Modifier) {
    Scaffold {
        paddingValues ->
        modifier.padding(paddingValues)
    }
}

@Preview
@Composable
fun MaldivesUnseenAppPreview() {
    MaldivesUnseenApp()
}